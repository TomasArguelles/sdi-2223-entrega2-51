const express = require('express');
const userSessionRouter = express.Router();

const STANDARD_USER_KIND = "Usuario Estándar";

userSessionRouter.use(function (req, res, next) {
    if (req.session.user && req.session.kind === STANDARD_USER_KIND) {
        next();
    } else {
        res.redirect("/users/login");
    }
});

module.exports = userSessionRouter;