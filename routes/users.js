const {generateLogContent} = require("../middlewares/loggerMiddleware");
module.exports = function (app, usersRepository) {
    app.get('/users', function (req, res) {
        generateLogContent(req, res);
        res.send('lista de usuarios');
    })

    app.get('/users/logout', function (req, res) {
        generateLogContent(req, res);
        req.session.user = null;
        res.redirect("/users/login" +
            "?message=El usuario se ha desconectado correctamente"+
            "&messageType=alert-success ");
    })

    app.get('/users/signup', function (req, res) {
        generateLogContent(req, res);
        res.render("signup.twig");
    })

    app.post('/users/signup', function (req, res) {
        let securePassword = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');
        let user = {
            email: req.body.email,
            password: securePassword
        }
        usersRepository.findUser({email: user.email}, {}).then(us => {
            if (us === null)
                usersRepository.insertUser(user).then(userId => {
                    res.redirect("/user/offers");
                })
            else
                res.redirect("/users/login");
        }).catch(error => {
            res.redirect("/users/signup" +
                "?message=Se ha producido un error al registrar el usuario"+
                "&messageType=alert-danger ");
        });
    });

    app.get('/users/login', function (req, res) {
        generateLogContent(req, res);
        res.render("login.twig");
    })

    /**
     * Suministrando su email y contraseña, un usuario registrado podrá autenticarse ante el sistema. Sólo los
     * usuarios que proporcionen correctamente su email y su contraseña podrán iniciar sesión con éxito.
     * En caso de que el inicio de sesión fracase, será necesario mostrar un mensaje de error indicando el
     * problema.
     * En caso de que el inicio de sesión sea correcto se enviará al usuiario a diferentes lugares:
     * * Usuario Administrador: “listado de todos los usuarios de la aplicación”
     * * Usuario Registrado: "listado de ofertas propias"
     */
    app.post('/users/login', function (req, res) {
        let securePassword = app.get("crypto").createHmac('sha256', app.get('clave')).update(req.body.password)
            .digest('hex')
        let filter = {
            email: req.body.email,
            password: securePassword
        }
        let options = {}
        usersRepository.findUser(filter, options).then(user => {
            if (user === null) {
                req.session.user = null;

                generateLogContent(req, res);

                res.redirect("/users/login" +
                    "?message=Email o password incorrecto"+
                    "&messageType=alert-danger ");
            } else {
                req.session.user = user.email;

                generateLogContent(req, res);
                if(user.kind === "Usuario Administrador"){
                    res.redirect("/user/offers"); //TODO “listado de todos los usuarios de la aplicación”
                }
                else{
                    res.redirect("/user/offers"); //listado de ofertas propias
                }
            }
        }).catch(error => {
            req.session.user = null;
            res.redirect("/users/login" +
                "?message=Se ha producido un error al buscar el usuario"+
                "&messageType=alert-danger ");
        })
    })
}