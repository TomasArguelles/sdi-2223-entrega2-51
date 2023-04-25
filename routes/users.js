module.exports = function (app, usersRepository) {
    app.get('/users', function (req, res) {
        res.send('lista de usuarios');
    })

    app.get('/users/logout', function (req, res) {
        req.session.user = null;
        res.send("El usuario se ha desconectado correctamente");
    })

    app.get('/users/signup', function (req, res) {
        res.render("signup.twig");
    })

    app.post('/users/signup', function (req, res) {
        let securePassword = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');
        let user = {
            email: req.body.email,
            password: securePassword
        }
        usersRepository.findUser({email: user.email}, {}).then(user => {
            if (user === null)
                usersRepository.insertUser(user).then(userId => {
                    res.redirect("/users/login" +
                        "?message=Nuevo usuario registrado"+
                        "&messageType=alert-info ");
                })
            else
                res.redirect("/users/login");
        }).catch(error => {
            res.redirect("/users/singup" +
                "?message=Se ha producido un error al registrar el usuario"+
                "&messageType=alert-danger ");
        });
    });

    app.get('/users/login', function (req, res) {
        res.render("login.twig");
    })

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
                res.redirect("/users/login" +
                    "?message=Email o password incorrecto"+
                    "&messageType=alert-danger ");
            } else {
                req.session.user = user.email;
                res.redirect("/publications");
            }
        }).catch(error => {
            req.session.user = null;
            res.redirect("/users/login" +
                "?message=Se ha producido un error al buscar el usuario"+
                "&messageType=alert-danger ");
        })
    })
}