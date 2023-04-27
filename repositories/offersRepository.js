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
    }
};