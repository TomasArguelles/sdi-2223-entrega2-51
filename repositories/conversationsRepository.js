module.exports = {
    mongoClient: null, app: null, init: function (app, mongoClient) {
        this.mongoClient = mongoClient;
        this.app = app;
    },
    getConversations: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'conversations';
            const conversationsCollection = database.collection(collectionName);
            return await conversationsCollection.find(filter, options).toArray();
        } catch (error) {
            throw (error);
        }
    },
    addConversation: function (conv, callbackFunction) {
        try {
            this.mongoClient.connect(this.app.get('connectionStrings'), function (err, dbClient) {
                if (err) {
                    callbackFunction(null)
                } else {
                    const database = dbClient.db("sdi-2223-entrega2-51");
                    const collectionName = 'conversations';
                    const conversationsCollection = database.collection(collectionName);
                    conversationsCollection.insertOne(conv)
                        .then(result => callbackFunction(result.insertedId))
                        .then(() => dbClient.close())
                        .catch(err => callbackFunction({error: err.message}));
                }
            });
        } catch (error) {
            throw (error);
        }
    },

    findConversation: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'conversations';
            const conversationsCollection = database.collection(collectionName);
            return await conversationsCollection.findOne(filter, options);
        } catch (error) {
            throw (error);
        }
    },


    deleteConversation: async function (filter, options) {
        try {
            const client = await this.mongoClient.connect(this.app.get('connectionStrings'));
            const database = client.db("sdi-2223-entrega2-51");
            const collectionName = 'conversations';
            const conversationsCollection = database.collection(collectionName);
            const result = await conversationsCollection.deleteOne(filter, options);
            return result;
        } catch (error) {
            throw (error);
        }
    }
}