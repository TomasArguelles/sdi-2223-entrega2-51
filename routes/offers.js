const {ObjectId} = require("mongodb");
const {validationResult} = require('express-validator');
const {
    validateAddOfferFields,
    validateRemoveOfferFields
} = require("../middlewares/validationMiddleware");
const {formatDate} = require("../util/dateUtils");

const ADD_OFFER_VIEW = "offers/add";
const LIST_OFFERS_VIEW = "offers/offersList";
const LIST_USER_OFFERS_VIEW = "offers/listUserOffers";

const OFFERS_ENPOINT = "/offers";

const USER_OFFERS_ENDPOINT = "/user/offers"; // Pathname de la vista

module.exports = function (app, offersRepository) {

    /**
     * GET /offers/add
     * Muestra el formulario para añadir una nueva oferta
     */
    app.get("/offer/add", function (req, res) {
        res.render(ADD_OFFER_VIEW);
    });

    /**
     * POST /offers/add
     * Añade una nueva oferta a la base de datos.
     */
    app.post("/offer/add", validateAddOfferFields(), async function (req, res) {
        try {
            const errors = validationResult(req);

            if (!errors.isEmpty()) {
                res.render(ADD_OFFER_VIEW, {errors: errors.array()});

            } else {
                let {title, description, price, featured} = req.body;

                // W12 Marcar oferta como destacada
                if (featured && req.session.user.wallet < 20)
                    res.send("Dinero insuficiente para destacar la oferta (precio: 20€)");
                else if (featured && req.session.user.wallet >= 20)
                    req.session.user.wallet -= 20; // coste de destacar una oferta

                const offer = {
                    title,
                    description,
                    price,
                    featured, //destacada (boolean)
                    date: new Date(),
                    seller: req.session.user
                };

                await offersRepository.addNewOffer(offer, (result) => {
                    if (result) {
                        res.redirect("/user/offers")
                    }
                });
            }

        } catch (err) {
            res.status(500).json({error: "Error al insertar la oferta. " + err});
        }
    });

    /**
     * GET /offers/
     *s
     * Muestra todas las ofertas publicadas por el usuario en sesion.
     */
    app.get("/user/offers", async function (req, res) {
        try {
            let page = parseInt(req.query.page); // Es String!!!
            if (typeof req.query.page === "undefined" || req.query.page === null || req.query.page === "0")
                page = 1;

            await offersRepository.getAllUserInSessionOffersPg(req.session.user, page).then(result => {
                let lastPage = result.total / 5;
                if (result.total % 5 > 0)
                    lastPage = lastPage + 1; // Sobran decimales
                let pages = []; // Páginas a mostrar
                for (let i = page - 2; i <= page + 2; i++) {
                    if (i > 0 && i <= lastPage)
                        pages.push(i);
                }

                // Listado con las fechas formateadas
                const formatedOffers = result.offers.map(offer => {
                    offer.date = formatDate(offer.date);
                    return offer;
                });

                let response = {
                    offers: formatedOffers,
                    pages: pages,
                    currentPage: page
                };
                res.render(LIST_OFFERS_VIEW, response);
            });

        } catch (err) {
            res.status(500).json({error: "Error al listar las ofertas"});
        }
    });

    app.get("/offer/:offerId/delete", async function (req, res) {
        try {
            const errors = validationResult(req);

            if (!errors.isEmpty()) {
                return res.status(422).json({errors: errors.array()});

            } else {

                const {offerId} = req.params;

                // Comprobar que la oferta pertenece al usuario en sesión
                const offer = await offersRepository.findOffer({_id: ObjectId(offerId)}, {});
                if (offer.seller !== req.session.user) {
                    res.status(403).json({error: "No tienes permisos para eliminar esta oferta"});
                } else {
                    await offersRepository.deleteOffer(ObjectId(offerId), (isDeleted) => {
                        if (isDeleted) {
                            req.pathname === USER_OFFERS_ENDPOINT ? res.redirect("/offers/all") : res.redirect("/user/offers");
                        }
                    });
                }
            }

        } catch (err) {
            res.status(500).json({error: "Error al eliminar la oferta"});
        }
    });

    /**
     * W9 Usuario registrado: Buscar ofertas
     */
    app.get("/offers/all", function (req, res) {
        if (req.session.user == null) { // ¿Usuario registrado?
            res.redirect("/offers");
            return;
        }
        let filter = {};
        let options = {sort: {title: 1}}; // Búsqueda de ofertas por título
        if (req.query.search != null && typeof (req.query.search) != "undefined" && req.query.search !== "") {
            // Si no se establece filtro, se tomarán todas las ofertas
            filter = {"title": {$regex: ".*" + String.toLowerCase(req.query.search) + ".*"}}; // Texto de la búsqueda
        }
        let page = parseInt(req.query.page); // Es String!!!
        if (typeof req.query.page === "undefined" || req.query.page === null || req.query.page === "0")
            page = 1;
        offersRepository.getOffersPg(filter, options, page).then(result => {
            let lastPage = result.total / 5;
            if (result.total % 5 > 0)
                lastPage = lastPage + 1; // Sobran decimales
            let pages = []; // Páginas a mostrar
            for (let i = page - 2; i <= page + 2; i++) {
                if (i > 0 && i <= lastPage)
                    pages.push(i);
            }
            let response = {
                offers: result.offers,
                pages: pages,
                currentPage: page
            };
            res.render(LIST_OFFERS_VIEW, response);
        }).catch(error => {
            res.send("Se ha producido un error al listar las ofertas " + error)
        });
    });

    /**
     * W10 Usuario registrado: Comprar oferta
     */
    app.get('/offers/buy/:id', function (req, res) {
        let offerId = ObjectId(req.params.id);
        let user = req.session.user;
        let filter = {_id: offerId};
        let options = {};
        offersRepository.findOffer(filter, options).then(async offer => {
            let check = await isBought(offer, options);
            if (!check && offer.seller.id !== user.id && offer.price <= user.wallet) {
                user.wallet -= offer.price;
                offersRepository.buyOffer({
                    user: user,
                    offerId: offerId
                }, function (offerId) {
                    if (offerId == null) {
                        user.wallet += offer.price; // deshacer operación
                        res.render(LIST_OFFERS_VIEW, {buyError: true});
                    } else
                        res.render(LIST_OFFERS_VIEW, {buyError: false});
                });
            } else
                res.send("Error al realizar la compra");
        });
    });

    /**
     * W10 Usuario registrado: Comprar oferta
     * Comprueba si fue comprada la oferta
     */
    function isBought(offer, options) {
        let filter = {offer_id: offer._id};
        return offersRepository.getPurchases(filter, options).then(purchases => {
            return purchases.length === 1;
        });
    }

    /**
     * W11 Usuario registrado: Listar ofertas compradas
     * Obtener las compras
     */
    app.get('/purchases', function (req, res) {
        let filter = {user: req.session.user};
        let options = {projection: {_id: 0, offerId: 1}};
        let page = parseInt(req.query.page); // Es String !!!
        if (typeof req.query.page === "undefined" || req.query.page === null || req.query.page === "0") {
            // Puede no venir el param
            page = 1;
        }
        offersRepository.getPurchasesPg(filter, options, page).then(purchasedIds => {
            let lastPage = purchasedIds.total / 5;
            if (purchasedIds.total % 5 > 0) {
                // Sobran decimales
                lastPage = lastPage + 1;
            }
            let pages = []; // Páginas a mostrar
            for (let i = page - 2; i <= page + 2; i++) {
                if (i > 0 && i <= lastPage) {
                    pages.push(i);
                }
            }
            let purchasedOffers = [];
            for (let i = 0; i < purchasedIds.length; i++) {
                purchasedOffers.push(purchasedIds[i].offerId);
            }
            let filter = {"_id": {$in: purchasedOffers}};
            let options = {sort: {title: 1}};
            offersRepository.getOffers(filter, options).then(offers => {
                res.render("purchase.twig", {
                    offers: offers,
                    pages: pages,
                    currentPage: page
                });
            }).catch(error => {
                res.send("Se ha producido un error al listar las ofertas compradas por el usuario: " + error);
            });
        }).catch(error => {
            res.send("Se ha producido un error al listar las ofertas " + error)
        });
    });

    /**
     * W12 Usuario registrado: Destacar oferta
     */
    app.get('/offers/featured/:id', function (req, res) {
        let offerId = ObjectId(req.params.id);
        let user = req.session.user;
        let filter = {_id: offerId};
        let options = {};
        offersRepository.findOffer(filter, options).then(async offer => {
            if (user.wallet >= 20) { // coste de destacar una oferta
                user.wallet -= 20;
                offer.featured = true;
                offersRepository.featuredOffer(offer, filter, options).then(result => {
                    if (result == null)
                        res.send("Error al destacar la oferta");
                    else
                        res.redirect(LIST_USER_OFFERS_VIEW);
                });
            } else
                res.send("Error al destacar la oferta");
        });
    });

};
