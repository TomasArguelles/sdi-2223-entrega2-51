const express = require('express');
const adminSessionRouter = express.Router();

const ADMIN_USER_KIND = "Usuario Administrador";
adminSessionRouter.use(function (req, res, next) {
    if (req.session.user && req.session.kind === ADMIN_USER_KIND) {
        next();
    } else {
        // Si el usuario no es administrador, se mostrará un mensaje de error
        // por pantalla
        res.redirect("/users/login" + "?message=Acción prohibida" + "&messageType=alert-danger ");
    }
});

module.exports = adminSessionRouter;