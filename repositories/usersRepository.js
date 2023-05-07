module.exports = {
    mongoClient: null, app: null, init: function (app, mongoClient) {
        this.mongoClient = mongoClient;
        this.app = app;
    }, findUser: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'users';
            const usersCollection = database.collection(collectionName);
            return await usersCollection.findOne(filter, options);
        } catch (error) {
            throw (error);
        }
    }, insertUser: async function (user) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'users';
            const usersCollection = database.collection(collectionName);
            const result = await usersCollection.insertOne(user);
            return result.insertedId;
        } catch (error) {
            throw (error);
        }
    },

    /**
     * Listado de usuarios registrados en el sistema.
     * Resultado paginado.
     * <p>
     * @param filter
     * @param options
     * @param page Página a mostrar.
     * @returns {Promise<{total: *, users: *}>}
     */
    findAllPg: async function (page, userInSession) {
        try {
            const limit = 5; // 5 ofertas por página

            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const userCollectionName = 'users';
            const usersCollection = database.collection(userCollectionName);

            const usersCollectionCount = await usersCollection.count();
            const cursor = usersCollection.find().skip((page - 1) * limit).limit(limit);
            const users = await cursor.toArray();
            const result = {
                users: users.filter(user => user.email !== "admin@email.com"),
                total: usersCollectionCount
            };

            return result;
        } catch (err) {
            throw(err)
        }
    }
};