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
                await offersRepository.deleteOffer(ObjectId(req.params.offerId), (isDeleted) => {
                    if(isDeleted){
                        res.redirect("/offers");
                    }
                });
            }

        } catch (err) {
            res.status(500).json({error: "Error al eliminar la oferta"});
        }
    });
};