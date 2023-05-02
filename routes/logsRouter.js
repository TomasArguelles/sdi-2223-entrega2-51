module.exports = function (app, logsRepository) {

    /**
     * GET /logs
     * Listado de todos los logs.
     */
    app.get("/admin", async function (req, res) {
        try {
            const logs = await logsRepository.findAllLogs();
            res.render("admin", {logs});
        } catch (err) {
            res.status(500).json({error: "Error al listar los logs. " + err});
        }
    });

    /**
     * GET /logs/delete
     * Elimina todos los logs.
     */
    app.get("/logs/delete", async function (req, res) {
        try {
            await logsRepository.deleteAllLogs(function (isDeleted) {
                if (isDeleted) {
                    res.redirect("/logs");
                }
            });

        } catch (err) {
            res.status(500).json({error: "Error al eliminar los logs. " + err});
        }
    });

    /**
     * GET /logs/:logType
     * Listado de logs, filtrado por tipo de log.
     */
    app.get("/logs/:logType", async function (req, res) {
        // TODO: Comprobar param
        try {
            const logs = await logsRepository.filterLogByType(req.params.logType);
            //res.render("logs/list", {logs});
        } catch (err) {
            res.status(500).json({error: "Error al listar los logs. " + err});
        }
    });
}