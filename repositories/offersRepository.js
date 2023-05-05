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

    deleteOffer: async function (offerId, callback) {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const offerCollection = database.collection(offersCollectionName);

        await offerCollection.deleteOne({_id: offerId}).then((result) => {
            callback(result.deletedCount === 1);
        });
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
    }
};