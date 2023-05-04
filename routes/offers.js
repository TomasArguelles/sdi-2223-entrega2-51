const {ObjectId} = require("mongodb");
const {validationResult} = require('express-validator');
const {
    validateAddOfferFields,
    validateRemoveOfferFields
} = require("../middlewares/validationMiddleware");
const {formatDate} = require("../util/dateUtils");

module.exports = function (app, offersRepository) {

    /**
     * GET /offers/add
     * Muestra el formulario para añadir una nueva oferta
     */
    app.get("/offer/add", function (req, res) {
        res.render("offer/add");
    });

    /**
     * POST /offers/add
     * Añade una nueva oferta a la base de datos.
     */
    app.post("/offer/add", validateAddOfferFields(), async function (req, res) {
        try {
            const errors = validationResult(req);

            if (!errors.isEmpty()) {
                res.render("offer/add", {errors: errors.array()});
                //return res.status(422).json({errors: errors.array()});

            } else {
                const {title, description, price} = req.body;

                const offer = {
                    title,
                    description,
                    price,
                    date: new Date(),
                    seller: req.session.user
                };

                await offersRepository.addNewOffer(offer, (result) => {
                    if (result) {
                        res.redirect("/offers")
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
    app.get("/offers", async function (req, res) {
        try {
            const offers = await offersRepository.getAllUserInSessionOffers(req.session.user);

            // Listado con las fechas formateadas
            const formatedOffers = offers.map(offer => {
                offer.date = formatDate(offer.date);
                return offer;
            });
            res.render("offer/list", {offers: formatedOffers});
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
                await offersRepository.deleteOffer(ObjectId(offerId), (isDeleted) => {
                    if(isDeleted){
                        res.redirect("/offers");
                    }
                });
            }

        } catch (err) {
            res.status(500).json({error: "Error al eliminar la oferta"});
        }
    });

    /**
     * W9 Usuario registrado: Buscar ofertas
     */
    app.get("/shop", function (req, res) {
        if (req.session.user == null) { // ¿Usuario registrado?
            res.redirect("/shop");
            return;
        }
        let filter = {};
        let options = {sort: {title: 1}}; // Búsqueda de ofertas por título
        if (req.query.search != null && typeof (req.query.search) != "undefined" && req.query.search !== "") {
            // Si no se establece filtro, se tomarán todas las ofertas
            filter = {"title": {$regex: ".*" + req.query.search + ".*"}}; // Texto de la búsqueda
        }
        let page = parseInt(req.query.page); // Es String!!!
        if (typeof req.query.page === "undefined" || req.query.page === null || req.query.page === "0") { //
            page = 1;
        }
        offersRepository.getOffersPg(filter, options, page).then(result => {
            let lastPage = result.total / 5;
            if (result.total % 5 > 0) {
                // Sobran decimales
                lastPage = lastPage + 1;
            }
            let pages = []; // Páginas a mostrar
            for (let i = page - 2; i <= page + 2; i++) {
                if (i > 0 && i <= lastPage) {
                    pages.push(i);
                }
            }
            let response = {songs: result.songs, pages: pages, currentPage: page};
            res.render("shop.twig", response);
        }).catch(error => {
            res.send("Se ha producido un error al listar las ofertas " + error)
        });
    });
};
