const {generateLogContent} = require("../middlewares/loggerMiddleware");
module.exports = function (app, usersRepository) {
    app.get('/users/list', function (req, res) {
            generateLogContent(req, res);

            try {
                let filter = {user: req.session.user};
                let page = parseInt(req.query.page);
                if (typeof req.query.page === "undefined" || req.query.page === null || req.query.page === "0") {
                    // Puede no venir el param
                    page = 1;
                }

                usersRepository.findAllPg(filter, {}, page).then(usersIds => {
                    let lastPage = usersIds.total / 5;
                    if (usersIds.total % 5 > 0) {
                        lastPage = lastPage + 1;
                    }
                    let pages = []; // Páginas a mostrar
                    for (let i = page - 2; i <= page + 2; i++) {
                        if (i > 0 && i <= lastPage) {
                            pages.push(i);
                        }
                    }

                    res.render("users/list", {
                        users: usersIds.users,
                        pages: pages,
                        currentPage: page
                    });
                });
            } catch
                (error) {
                console.log(error);
            }
        }
    )

    app.get('/users/logout', function (req, res) {
        generateLogContent(req, res);
        req.session.user = null;
        res.redirect("/users/login" +
            "?message=El usuario se ha desconectado correctamente" +
            "&messageType=alert-success ");
    })

    app.get('/users/signup', function (req, res) {
        generateLogContent(req, res);
        res.render("signup.twig");
    })

    app.post('/users/signup', function (req, res) {
        if (req.body.password !== req.body.passwordConfirm)
            res.redirect("/users/signup" +
                "?message=Se ha producido un error al registrar el usuario" +
                "&messageType=alert-danger ");
        else {
            let securePassword = app.get("crypto").createHmac('sha256', app.get('clave'))
                .update(req.body.password).digest('hex');

            const {email, name, surname, date} = req.body;

            let user = {
                email,
                name,
                surname,
                date,
                kind: "Usuario Estándar",
                password: securePassword
            }

            usersRepository.findUser({email: user.email}, {}).then(us => {
                if (us === null)
                    usersRepository.insertUser(user).then(userId => {
                        res.redirect("/user/offers");
                    })
                else
                    res.redirect("/users/signup" +
                        "?message=Se ha producido un error al registrar el usuario" +
                        "&messageType=alert-danger ");
            }).catch(error => {
                res.redirect("/users/signup" +
                    "?message=Se ha producido un error al registrar el usuario" +
                    "&messageType=alert-danger ");
            });
        }
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
                    "?message=Email o password incorrecto" +
                    "&messageType=alert-danger ");
            } else {
                req.session.user = user.email;
                req.session.kind = user.kind;

                generateLogContent(req, res);
                if (user.kind === "Usuario Administrador") {
                    res.redirect("/users/list"); //listado de todos los usuarios de la aplicación
                } else {
                    res.redirect("/user/offers"); // listado de ofertas propias
                }
            }
        }).catch(error => {
            req.session.user = null;
            res.redirect("/users/login" +
                "?message=Se ha producido un error al buscar el usuario" +
                "&messageType=alert-danger ");
        })
    })
}