const express = require('express');
const adminSessionRouter = express.Router();

const ADMIN_USER_KIND = "Usuario Administrador";
adminSessionRouter.use(function (req, res, next) {
    if (req.session.user && req.session.kind === ADMIN_USER_KIND) {
        next();
    } else {
        //res.redirect("/users/login");

        // Si el usuario no es administrador, se mostrará un mensaje de error
        // por pantalla
        document.body.prepend(`
            <div class="alert alert-danger">Acción prohibida</div>
        `)
    }
});

module.exports = adminSessionRouter;