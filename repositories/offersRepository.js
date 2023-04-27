module.exports = {
    mongoClient: null,
    app: null,
    init: function (app, mongoClient) {
        this.mongoClient = mongoClient;
        this.app = app;
    },

    addNewOffer: function (offer, callback) {
        // TODO:
        this.mongoClient.connect(this.app.get('connectionStrings'), function (err, dbClient) {
            if (err) {
                callback(err);
            } else {
                const database = dbClient.db("sdi-2223-entrega2-51");
                const collectionName = 'songs';
                const songsCollection = database.collection(collectionName);
                songsCollection.insertOne(song)
                    .then(result => callbackFunction(result.insertedId))
                    .then(() => dbClient.close());
            }
        });
    }
};