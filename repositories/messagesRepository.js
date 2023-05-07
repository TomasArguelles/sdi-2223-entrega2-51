module.exports = {
    mongoClient: null, app: null, init: function (app, mongoClient) {
        this.mongoClient = mongoClient;
        this.app = app;
    },
    getMessages: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'messages';
            const messagesCollection = database.collection(collectionName);
            return await messagesCollection.find(filter, options).toArray();
        } catch (error) {
            throw (error);
        }
    },
    addMessage: async function (message, callback) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'messages';
            const messagesCollection = database.collection(collectionName);
            await messagesCollection.insertOne(message).then((result) => {
                callback(result.insertedId);
            }).then(() => {
                client.close();
            });

        } catch (err) {
            throw `Error addNewOffer: ${err}`;
        }
    },

    markAsReadMessage: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'messages';
            const messagesCollection = database.collection(collectionName);
            // Modificamos el objeto con los campos a actualizar
            const updateObj = {
                $set: {leido: true} // Actualizamos el campo "leido" a true
            };
            const result = await messagesCollection.updateOne(filter, updateObj, options); // Utilizamos updateOne() para actualizar un documento en la colección

            return result;
        } catch (error) {
            throw (error);
        }
    },
    deleteMessage: async function (offerId, callback) {
        const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
        const database = client.db("sdi-2223-entrega2-51");
        const offerCollection = database.collection('messages');

        await offerCollection.deleteOne({_id: offerId}).then((result) => {
            callback(result.deletedCount === 1);
        });
    },
}