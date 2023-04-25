module.exports = function (app) {
    app.get('/authors', function (req, res) {
        let authors = [{
            "name": "Blank space",
            "group": "1.2",
            "rol": "cantante"
        }, {
            "name": "Blank space2",
            "group": "1.22",
            "rol": "teclista"
        }, {
            "name": "Blank space3",
            "group": "1.23",
            "rol": "bateria"
        }];

        let response = {
            seller: "Tienda de autores",
            authors: authors
        };
        res.render("authors/authors.twig", response);
    });

    app.get("/authors/add", function (req, res) {
        let rols = ["cantante", "bateria", "guitarrista", "bajista", "teclista"]
        let response = {
            rols: rols
        };
        res.render("authors/add.twig", response);
    });

    app.post('/authors/add', function (req, res) {
        let response;
        if (req.body.name === "undefined" || req.body.name === "")
            response = "Autor agregada: Nombre no enviado en la petición" + "<br>"
        else
            response = "Autor agregada: " + req.body.name + "<br>"
        if (req.body.group === "undefined" || req.body.group === "")
            response += "grupo: Grupo no enviado en la petición" + "<br>"
        else
            response += "grupo: " + req.body.group + "<br>"
        if (req.body.rol === "undefined" || req.body.rol === "")
            response += "rol: Rol no enviado en la petición" + "<br>"
        else
            response += "rol: " + req.body.rol
        res.send(response);
    });

    app.get('/authors/:id', function (req, res) {
        res.redirect("/authors");
    });
};