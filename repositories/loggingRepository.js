const loggingCollectionName = 'logs';

module.exports = {
    mongoClient: null,
    app: null,
    init: function (app, mongoClient) {
        this.mongoClient = mongoClient;
        this.app = app;
    },
    /**
     * Insertar un nuevo log en la base de datos.
     * El contenido del log es un objeto con la siguiente estructura:
     * <code>
     *     <ul>
     *         <li>type: Tipo de log</li>
     *         <li>
     *             description: Descripcion detallada del log
     *              <ul>
     *                  <li>method: Metodo de la peticion</li>
     *                  <li>url: Url de la peticion</li>
     *                  <li>params: Parametros de la peticion</li>
     *              </ul>
     *         </li>
     *         <li>timestamp: Fecha de generación del log</li>
     *    </ul>
     *
     * @param log
     * @param callback
     * @returns {Promise<void>}
     */
    addNewLog: async function (log, callback) {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const loggingCollection = database.collection(loggingCollectionName);
        await loggingCollection.insertOne(log).then((result) => {
            callback(result.insertedId);
        }).then(() => {
            client.close();
        });
    },

    /**
     * Devuelve los logs pertenecientes al tipo indicado.
     *
     * @param type Tipo de log a filtrar.
     * @param callback
     * @returns {Promise<void>}
     */
    filterLogByType: async function (type) {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const loggingCollection = database.collection(loggingCollectionName);

        const filteredLogs = await loggingCollection
            .find({type: type})
            .sort({timestamp: -1}) // Ordenamos por fecha descendente
            .toArray();
        return filteredLogs;
    },

    /**
     * Devuelve todos los logs registrados en la base de datos.
     * @returns {Promise<*>}
     */
    findAllLogs: async function () {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const loggingCollection = database.collection(loggingCollectionName);

        const allRegisteredLogs = await loggingCollection
            .find({})
            .sort({timestamp: -1}) // Ordenamos por fecha descendente
            .toArray();

        return allRegisteredLogs;
    },

    /**
     * Elimina todos los logs de la base de datos.
     *
     * @param callback
     * @returns {Promise<void>}
     */
    deleteAllLogs: async function (callback) {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const loggingCollection = database.collection(loggingCollectionName);

        await loggingCollection.deleteMany({}).then((result) => {
            callback(result.deletedCount === 1);
        });
    }
}