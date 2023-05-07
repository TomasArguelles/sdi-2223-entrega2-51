module.exports = function (app, logsRepository) {

    /**
     * GET /logs
     * Listado de todos los logs.
     */
    app.get("/admin", async function (req, res) {
        try {
            const logs = await logsRepository.findAllLogs();
            res.render("admin", {logs, sessionUser: req.session.user});
        } catch (err) {
            res.status(500).json({error: "Error al listar los logs. " + err});
        }
    });

    /**
     * GET /logs/delete
     * Elimina todos los logs.
     */
    app.get("/logs/delete/all", function (req, res) {
        try {
            logsRepository.deleteAllLogs(function (isDeleted) {
                res.redirect("/admin");
            });

        } catch (err) {
            res.status(500).json({error: "Error al eliminar los logs. " + err});
        }
    });

    /**
     * GET /logs/:logType
     * Listado de logs, filtrado por tipo de log.
     */
    app.get("/logs/filter", async function (req, res) {

        // TODO: Validar que el tipo de log es correcto (param)

        const {logType} = req.query;

        if (logType === 'TODO') {
            res.redirect("/admin");
            return;
        }

        try {
            const logs = await logsRepository.filterLogByType(logType);
            res.render("admin", {logs, sessionUser: req.session.user});

        } catch (err) {
            res.status(500).json({error: "Error al listar los logs. " + err});
        }
    });
}