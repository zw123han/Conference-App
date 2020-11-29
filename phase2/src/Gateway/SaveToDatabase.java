package Gateway;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

import java.util.ArrayList;

public class SaveToDatabase {

    MongoClient mongoClient;
    ArrayList<Savable> savables;

    public SaveToDatabase(ArrayList<Savable> savables) {
        this.savables = savables;

    }

    public boolean save() {
        try {
            mongoClient = new MongoClient( new MongoClientURI("mongodb://localhost:27017") );

            System.out.println("Connected to MongoDB!");
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        } finally {
            DB database = mongoClient.getDB("ConferenceApp");

            for (Savable sv : savables) {
                CollectionManager collectionManager = new CollectionManager(database, sv.getCollectionName(),
                        sv.getConversionStrategy());
                collectionManager.insertToCollection(sv);
            }

            if(mongoClient!=null) {
                mongoClient.close();
            }
            return true;
        }
    }

}
