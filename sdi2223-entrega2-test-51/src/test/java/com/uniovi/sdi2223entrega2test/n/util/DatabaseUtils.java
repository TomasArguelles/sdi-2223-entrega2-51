package com.uniovi.sdi2223entrega2test.n.util;

import com.mongodb.MongoClient;

public class DatabaseUtils {

    private MongoClient mongoClient;

    public static String DATABASE_NAME = "sdi-2223-entrega2-51";
    public static String OFFERS_COLLECTION_NAME = "offers";

    public DatabaseUtils() {
        mongoClient = new MongoClient("localhost", 27017);
    }

    /**
     * Elimina la colección de ofertas de la base de datos.
     */
    public static void resetOffersCollection() {
        DatabaseUtils dbUtils = new DatabaseUtils();
        dbUtils.mongoClient.getDatabase(DATABASE_NAME).getCollection(OFFERS_COLLECTION_NAME).drop();
        dbUtils.mongoClient.close();
    }
}
