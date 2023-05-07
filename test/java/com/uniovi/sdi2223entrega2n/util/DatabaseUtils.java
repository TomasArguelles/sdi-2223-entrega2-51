package com.uniovi.sdi2223entrega2n.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DatabaseUtils {

    private MongoClient mongoClient;

    public static String DATABASE_NAME = "sdi-2223-entrega2-51";
    public static String OFFERS_COLLECTION_NAME = "offers";
    public static String USERS_COLLECTION_NAME = "users";

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

    public static void resetUsersCollection() {
        DatabaseUtils dbUtils = new DatabaseUtils();
        dbUtils.mongoClient.getDatabase(DATABASE_NAME).getCollection(USERS_COLLECTION_NAME).drop();
        dbUtils.mongoClient.close();
    }

    public static void seedUsers() {
        DatabaseUtils dbUtils = new DatabaseUtils();
        MongoCollection<Document> collection = dbUtils.mongoClient.getDatabase(DATABASE_NAME).getCollection(USERS_COLLECTION_NAME);
        collection.drop();

        // Usuario administrador
        collection.insertOne(new Document()
                .append("email", "admin@email.com")
                .append("name", "admin")
                .append("surname", "admin")
                .append("kind", "Usuario Administrador")
                .append("password", "ebd5359e500475700c6cc3dd4af89cfd0569aa31724a1bf10ed1e3019dcfdb11"));

        // user01
        collection.insertOne(new Document()
                .append("email", "user01@email.com")
                .append("name", "User01")
                .append("surname", "User01")
                .append("kind", "Usuario Estándar")
                .append("password", "783a825ecf667676312bdd5e76a138e61e55d5061ef541f445e0c22e671eba9f"));

        // user02
        collection.insertOne(new Document()
                .append("email", "user02@email.com")
                .append("name", "User02")
                .append("surname", "User02")
                .append("kind", "Usuario Estándar")
                .append("password", "9812db3294b48b41aa0ea4cbe44453280286c0a089a07f3f6f4a313759012ab9"));

        // user03
        collection.insertOne(new Document()
                .append("email", "user03@email.com")
                .append("name", "User03")
                .append("surname", "User03")
                .append("kind", "Usuario Estándar")
                .append("password", "66e93521a9447f5082b957a3bc07dcd5841608a0a2a951cbb30a21d83f13d83a"));

        // user04
        collection.insertOne(new Document()
                .append("email", "user04@email.com")
                .append("name", "User04")
                .append("surname", "User04")
                .append("kind", "Usuario Estándar")
                .append("password", "049141fc2f265c205539050c6ebc6a9a3d81c87fbf8f936030bc108330280fef"));

        // user05
        collection.insertOne(new Document()
                .append("email", "user05@email.com")
                .append("name", "User05")
                .append("surname", "User05")
                .append("kind", "Usuario Estándar")
                .append("password", "bbd89bd814f8c02da5534ee62781701285aa3d586158e9c857618f9a36d18ce3"));

        // user06
        collection.insertOne(new Document()
                .append("email", "user06@email.com")
                .append("name", "User06")
                .append("surname", "User06")
                .append("kind", "Usuario Estándar")
                .append("password", "308f5766b85b7842f62e0bd96873a80cad47cf9fb76ba1061957ad0f1ae4dc1f"));

        // user07
        collection.insertOne(new Document()
                .append("email", "user07@email.com")
                .append("name", "User07")
                .append("surname", "User07")
                .append("kind", "Usuario Estándar")
                .append("password", "1a44cfeba26b0d6245a787a87a39e90308bcc6445916951d2cb9a80881c7d1c6"));

        // user08
        collection.insertOne(new Document()
                .append("email", "user08@email.com")
                .append("name", "User08")
                .append("surname", "User08")
                .append("kind", "Usuario Estándar")
                .append("password", "60f45cdcacc671c9bc3cddc0e4cf4d5aebe65d5a674fdb7498632a2427638df3"));

        // user09
        collection.insertOne(new Document()
                .append("email", "user09@email.com")
                .append("name", "User09")
                .append("surname", "User09")
                .append("kind", "Usuario Estándar")
                .append("password", "c7b08c4af12093cbf088edac0b8c9e394f8486cdb267f5950f363bd5adb93e77"));

        // user10
        collection.insertOne(new Document()
                .append("email", "user10@email.com")
                .append("name", "User10")
                .append("surname", "User10")
                .append("kind", "Usuario Estándar")
                .append("password", "c4307e1f4f7a11e99a5e4bfeaf598cadcb99fc536b6eeb668ebdff87df88cff5"));

        // user11
        collection.insertOne(new Document()
                .append("email", "user11@email.com")
                .append("name", "User11")
                .append("surname", "User11")
                .append("kind", "Usuario Estándar")
                .append("password", "b9809187151d1b2ada397b7c6ecfdedcaeeaa401568f7abf4706b7eab2f7f8b6"));

        // user12
        collection.insertOne(new Document()
                .append("email", "user12@email.com")
                .append("name", "User12")
                .append("surname", "User12")
                .append("kind", "Usuario Estándar")
                .append("password", "6fc8f37f8afb07a2aadeb9d6a81df0c4f3fe8d62ed7f41d8a6b0d797011c310c"));

        // user13
        collection.insertOne(new Document()
                .append("email", "user13@email.com")
                .append("name", "User13")
                .append("surname", "User13")
                .append("kind", "Usuario Estándar")
                .append("password", "46ef749ceaff17681fcbcf4f5637387421db020f80a3624459e2702187283527")
        );

        // user14
        collection.insertOne(new Document()
                .append("email", "user14@email.com")
                .append("name", "User14")
                .append("surname", "User14")
                .append("kind", "Usuario Estándar")
                .append("password", "c76a26044ac42d88b2154b69e5f7006d38140ccbf91c1a93679ba78e0cc54a17"));

        // user15
        collection.insertOne(new Document()
                .append("email", "user15@email.com")
                .append("name", "User15")
                .append("surname", "User15")
                .append("kind", "Usuario Estándar")
                .append("password", "5d6c0f50beb06679b619f7ef2aa46ca23caae057b21b1621ba5ae9692a9192fb"));

        dbUtils.mongoClient.close();
    }

    public static void seedUsersAlt() {
        DatabaseUtils dbUtils = new DatabaseUtils();
        MongoCollection<Document> collection = dbUtils.mongoClient.getDatabase(DATABASE_NAME).getCollection(USERS_COLLECTION_NAME);
        collection.drop();

        // Usuario administrador
        collection.insertOne(new Document()
                .append("email", "admin@email.com")
                .append("name", "admin")
                .append("surname", "admin")
                .append("kind", "Usuario Administrador")
                .append("password", "ebd5359e500475700c6cc3dd4af89cfd0569aa31724a1bf10ed1e3019dcfdb11"));

        // user01
        collection.insertOne(new Document()
                .append("email", "user01@email.com")
                .append("name", "User01")
                .append("surname", "User01")
                .append("kind", "Usuario Estándar")
                .append("password", "783a825ecf667676312bdd5e76a138e61e55d5061ef541f445e0c22e671eba9f"));

        // user02
        collection.insertOne(new Document()
                .append("email", "user02@email.com")
                .append("name", "User02")
                .append("surname", "User02")
                .append("kind", "Usuario Estándar")
                .append("password", "9812db3294b48b41aa0ea4cbe44453280286c0a089a07f3f6f4a313759012ab9"));

        // user03
        collection.insertOne(new Document()
                .append("email", "user03@email.com")
                .append("name", "User03")
                .append("surname", "User03")
                .append("kind", "Usuario Estándar")
                .append("password", "66e93521a9447f5082b957a3bc07dcd5841608a0a2a951cbb30a21d83f13d83a"));

        // user04
        collection.insertOne(new Document()
                .append("email", "user04@email.com")
                .append("name", "User04")
                .append("surname", "User04")
                .append("kind", "Usuario Estándar")
                .append("password", "049141fc2f265c205539050c6ebc6a9a3d81c87fbf8f936030bc108330280fef"));

        dbUtils.mongoClient.close();
    }
}
