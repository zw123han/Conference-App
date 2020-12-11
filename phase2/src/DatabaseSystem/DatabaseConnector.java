package DatabaseSystem;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

public class DatabaseConnector {

    MongoClient mongoClient;

    public DatabaseConnector(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public DB connect() {
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://admin:82Fo6ZA64t3gSNRk@conferenceapp.pfgil.mongodb.net/ConferenceApp?retryWrites=true&w=majority") );

            System.out.println("Connected to MongoDB!");
            return mongoClient.getDB("ConferenceApp");
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void disconnect() {
        if(mongoClient!=null) {
            mongoClient.close();
        }
    }
}


