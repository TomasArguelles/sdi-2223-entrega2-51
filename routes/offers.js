const {ObjectId} = require("mongodb");
module.exports = function (app, offersRepository) {
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
}