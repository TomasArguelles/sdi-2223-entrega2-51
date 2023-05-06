const express = require('express');
const log4js = require("log4js");
const logginRepository = require("../repositories/loggingRepository");
const logger = log4js.getLogger();

const loggerRouter = express.Router();

log4js.configure({
    appenders: {
        fileAppender: {type: "file", filename: "logs/requests.log"},
        console: {type: "console"}
    }, categories: {
        default: {appenders: ["fileAppender", "console"], level: "debug"}

    }
});

// Tipos de logs en la aplicación
const LOG_TYPES = {
    PET: "PET", // Cualquier peticion HTTP
    ALTA: "ALTA", // Alta de un usuario
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
    if (req.url.includes("login") && req.method === "POST" && req.session.user && req.url !== "/users") {
        return LOG_TYPES.LOGIN_EX;
    }

    // Login incorrecto
    if (req.url.includes("login") && req.method === "POST" && !req.session.user && req.url !== "/users") {
        return LOG_TYPES.LOGIN_ERR;
    }

    // Alta de usuario
    if (req.url.includes("signup") && (req.method === "POST" || req.method === "GET") && req.url !== "/users") {
        return LOG_TYPES.ALTA;
    }

    // Cierre de sesión
    if (req.url.includes("logout") && req.method === "GET") {
        return LOG_TYPES.LOGOUT;
    }

    // Cualquier otra petición
    return LOG_TYPES.PET;
}

/**
 * Genera el contenido del log.
 * @param req
 * @param res
 * @returns {{description: {method, params, url: (*|string|string)}, type: string, timestamp: string}}
 */
const generateLogContent = async function (req, res) {
    const logContent = {
        type: getLogType(req, res), description: {
            method: req.method,
            url: req.originalUrl.includes("?") ? req.originalUrl.split('?')[0]
                : req.originalUrl,
            status: res.statusCode,
            ip: req.remoteAddress || req.ip,
            family: req.connection.remoteFamily,
            agent: req.headers['user-agent'],
            httpVersion: req.httpVersion,
        }, timestamp: new Date().toISOString(),
    }

    const logType = getLogType(req, res);

    // Si la petición es de logout, añadimos el usuario a la descripción del log
    if (logType === LOG_TYPES.LOGOUT) {
        logContent.description.user = req.session.user;
    }

    // Si la peticion contiene parametros, los añadimos al contenido del log
    if (req.query && Object.keys(req.query).length > 0) {
        const allParams = req.query;
        const splitedParams = Object.keys(allParams).map(key => {
            return {
                key: key, value: allParams[key]
            };
        });
        logContent.description.params = splitedParams;
    }

    await logginRepository.addNewLog(logContent, (id) => {
        console.log(`Log ${id} registrado correctamente.`);
    });
}

loggerRouter.use(function (req, res, next) {


    // Si la url no está en la lista de excluidos, la logueamos
    if (isUrlIncludedToLog(req.originalUrl)) {
        generateLogContent(req, res);
        //logger.debug(logContent);
    }
    next();
});

module.exports = {loggerRouter, generateLogContent};

