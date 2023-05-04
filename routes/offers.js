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
    /**
     * W10 Usuario registrado: Comprar oferta
     */
    app.get('/offers/buy/:id', function (req, res) {
        let offerId = ObjectId(req.params.id);
        let shop = {user: req.session.user, offerId: offerId};
        let filter = {_id: offerId};
        let options = {};
        offersRepository.findOffer(filter, options).then(async offer => {
            let check = await isBought(req.session.user, offer, options);
            if (!check && offer.price <= req.session.user.money) {
                offersRepository.buyOffer(shop, function (offerId) {
                    if (offerId == null) {
                        res.send("Error al realizar la compra");
                    } else {
                        res.redirect("/purchases");
                    }
                });
            }
        });
    });
    /**
     * W10 Usuario registrado: Comprar oferta
     * Comprueba si fue comprada la oferta
     */
    function isBought(user, offer, options) {
        let filter = {offer_id: offer._id, user: user};
        return offersRepository.getPurchases(filter, options).then(purchases => {
            return purchases.length === 1 || user === offer.user;
        });
    }
    /**
     * W10 Usuario registrado: Comprar oferta
     * Obtener las compras
     */
    app.get('/purchases', function (req, res) {
        let filter = {user: req.session.user};
        let options = {projection: {_id: 0, offerId: 1}};
        offersRepository.getPurchases(filter, options).then(purchasedIds => {
            let purchasedOffers = [];
            for (let i = 0; i < purchasedIds.length; i++) {
                purchasedOffers.push(purchasedIds[i].offerId);
            }
            let filter = {"_id": {$in: purchasedOffers}};
            let options = {sort: {title: 1}};
            offersRepository.getOffers(filter, options).then(offers => {
                res.render("purchase.twig", {offers: offers});
            }).catch(error => {
                res.send("Se ha producido un error al listar las ofertas compradas por el usuario: " + error);
            });
        }).catch(error => {
            res.send("Se ha producido un error al listar las ofertas del usuario " + error);
        });
    });
}