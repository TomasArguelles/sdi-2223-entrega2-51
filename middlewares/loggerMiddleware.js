const express = require('express');
const log4js = require("log4js");
const logger = log4js.getLogger();

const loggerRouter = express.Router();

log4js.configure({
    appenders: {
        fileAppender: {type: "file", filename: "logs/requests.log"},
        console: {type: "console"}
    },
    categories: {
        default: {appenders: ["fileAppender", "console"], level: "debug"}

    }
});

// Tipos de logs en la aplicación
const LOG_TYPES = {
    PET: "PET", // Cualquier peticion HTTP
    LOGIN_EX: "LOGIN_EX", // Login exitoso
    LOGIN_ERR: "LOGIN_ERR", // Login incorrecto
    LOGOUT: "LOGOUT", // Cierre de sesión
}

// Urls que no queremos que se logueen
const excludedUrls = ["/images", "/css", "/js"];

const isUrlIncludedToLog = function (pathToCheck) {
    return excludedUrls.filter(url => url.includes(pathToCheck)).length === 0;
}

/**
 * Determina el tipo de log que se va a realizar en función de la petición.
 *
 * @param req Petición HTTP recibida
 * @param res Respuesta HTTP enviada
 *
 * @returns {string}
 */
const getLogType = function (req, res) {

    // Login realizado correctamente
    if (req.url.includes("login") && req.method === "POST"
        && res.status === 200 && req.session.user) {
        return LOG_TYPES.LOGIN_EX;
    }

    // Login incorrecto
    if (req.url.includes("login") && req.method === "POST" && !req.session.user) {
        return LOG_TYPES.LOGIN_ERR;
    }

    // Cierre de sesión
    if (req.url.includes("logout") && req.method === "GET") {
        return LOG_TYPES.LOGOUT;
    }

    // Cualquier otra petición
    return LOG_TYPES.PET;
}

loggerRouter.use(function (req, res, next) {

    // Obtener la url de la petición (basename)
    const urlBasename = req.originalUrl

    // Texto descriptivo del log
    let logDescription = {
        method: req.method,
        url: req.originalUrl,
    }

    if(req.params && Object.keys(req.params).length > 0){
        logDescription.params = req.params;
    }

    const logContent = {
        type: getLogType(req, res),
        description: logDescription,
        timestamp: new Date().toISOString(),
    }

    // Si la url no está en la lista de excluidos, la logueamos
    if (isUrlIncludedToLog(urlBasename)) {
        console.log("logger: ", logContent);
        logger.debug(logContent);
    }
    //logger.debug("request received. url: " + urlBasename);
    next();
});

module.exports = loggerRouter;

