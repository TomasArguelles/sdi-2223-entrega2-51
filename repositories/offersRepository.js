const offersCollectionName = 'offers';

module.exports = {
    mongoClient: null,
    app: null,
    init: function (app, mongoClient) {
        this.mongoClient = mongoClient;
        this.app = app;
    },

    /**
     * Añade una nueva oferta con la siguiente informacion:
     * <code>
     *     <ul>
     *         <li>title: Titulo de la oferta</li>
     *         <li>description: Descripcion detallada de la oferta</li>
     *         <li>price: Cantidad solicitada</li>
     *         <li>date: Fecha de creacion</li>
     *      </ul>
     * </code>
     *
     * @param offer
     * @param callback
     */
    addNewOffer: async function (offer, callback) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const offerCollection = database.collection(offersCollectionName);
            await offerCollection.insertOne(offer).then((result) => {
                callback(result.insertedId);
            }).then(() => {
                client.close();
            });

        } catch (err) {
            throw `Error addNewOffer: ${err}`;
        }
    },

    deleteOffer: async function (offerId, callback) {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const offerCollection = database.collection(offersCollectionName);

        await offerCollection.deleteOne({_id: offerId}).then((result) => {
            callback(result.deletedCount === 1);
        });
    },

    /**
     * Devuelve todas las ofertas publicadas por el usuario en sesion.
     *
     * @param userInSessionEmail Email del usuario en sesion.
     * @param callback
     */
    getAllUserInSessionOffers: async function (userInSessionEmail) {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const offerCollection = database.collection(offersCollectionName);
        const offers = await offerCollection.find({seller: userInSessionEmail}).toArray();

        return offers;
    },

    /**
     * W9 Usuario registrado: Buscar ofertas
     * Devuelve todas las ofertas de los usuarios.
     * Puede recibir un filtro (título).
     */
    getOffers: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const offerCollection = database.collection(offersCollectionName);
            const offers = await offerCollection.find(filter, options).toArray();
            return offers;
        } catch (error) {
            throw (error);
        }
    },

    /**
     * W9 Usuario registrado: Buscar ofertas
     * Devuelve las ofertas correspondientes a una página concreta (paginación).
     */
    getOffersPg: async function (filter, options, page) {
        try {
            const limit = 5; // 5 ofertas por página
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const offersCollection = database.collection(offersCollectionName);
            const offersCollectionCount = await offersCollection.count();
            const cursor = offersCollection.find(filter, options).skip((page - 1) * limit).limit(limit);
            const offers = await cursor.toArray();
            const result = {offers: offers, total: offersCollectionCount};
            return result;
        } catch (error) {
            throw (error);
        }
    },

    /**
     * W10 Usuario registrado: Comprar oferta
     */
    buyOffer: function (shop, callbackFunction) {
        this.mongoClient.connect(this.app.get('connectionStrings'), function (err, dbClient) {
            if (err) {
                callbackFunction(null)
            } else {
                const database = dbClient.db("sdi-2223-entrega2-51");
                const collectionName = 'purchases';
                const purchasesCollection = database.collection(collectionName);
                purchasesCollection.insertOne(shop)
                    .then(result => callbackFunction(result.insertedId))
                    .then(() => dbClient.close())
                    .catch(err => callbackFunction({error: err.message}));
            }
        });
    },

    /**
     * W10 Usuario registrado: Comprar oferta
     * Obtiene las ofertas compradas
     */
    getPurchases: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'purchases';
            const purchasesCollection = database.collection(collectionName);
            return await purchasesCollection.find(filter, options).toArray();
        } catch (error) {
            throw (error);
        }
    },

    /**
     * W11 Usuario registrado: Listar ofertas compradas
     * Devuelve las ofertas correspondientes a una página concreta (paginación).
     */
    getPurchasesPg: async function (filter, options, page) {
        try {
            const limit = 5; // 5 ofertas por página
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'purchases';
            const purchasesCollection = database.collection(collectionName);
            const purchasesCollectionCount = await purchasesCollection.count();
            const cursor = purchasesCollection.find(filter, options).skip((page - 1) * limit).limit(limit);
            const purchases = await cursor.toArray();
            const result = {purchases: purchases, total: purchasesCollectionCount};
            return result;
        } catch (error) {
            throw (error);
        }
    },
};