package DatabaseSystem;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

/**
 * DatabaseConnector contains method that connect/disconnect to a MongoDB database.
 *
 * @author  Jesse
 */
public class DatabaseConnector {

    MongoClient mongoClient;

    /**
     * Constructor that takes in a potentially null-value MongoClient instance.
     *
     * @param mongoClient       The MongoClient instance that is used to connect to the database
     */
    public DatabaseConnector(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    /**
     * Returns the database that the mongoClient retrieves from a specified URL. Returns null if the connection to the
     * database fails.
     *
     * @return      The DB object representing the MongoDB database.
     */
    public DB connect() {
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://admin:82Fo6ZA64t3gSNRk@conferenceapp.pfgil.mongodb.net/ConferenceApp?retryWrites=true&w=majority"));

            System.out.println("Connected to MongoDB!");
            return mongoClient.getDB("ConferenceApp");
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Closes the connection to the database that mongoClient connected to. Doesn't do anything if mongoClient has yet
     * to connect.
     */
    public void disconnect() {
        if(mongoClient!=null) {
            mongoClient.close();
        }
    }
}


