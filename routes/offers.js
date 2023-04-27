const {ObjectId} = require("mongodb");
module.exports = function (app, offersRepository) {

    /**
     * GET /offers/add
     * Formulario para añadir una nueva oferta
     */
    app.get("/offers/add", function (req, res) {
        res.render("offers/add");
    });
};