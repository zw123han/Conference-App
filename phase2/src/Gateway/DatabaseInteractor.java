package Gateway;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class DatabaseInteractor {

    DatabaseConnector dbConnector;
    DatabaseWriter dbWriter;
    DatabaseReader dbReader;
    MongoClient mongoClient;
    DB database;

    public DatabaseInteractor() {
        dbConnector = new DatabaseConnector(mongoClient);
        dbWriter = new DatabaseWriter();
        dbReader = new DatabaseReader();
    }

    public void saveToDatabase(ArrayList<Savable> savables) {
        dbWriter.save(savables, database);
    }

    public Savable readFromDatabase(Savable savable) {
        return dbReader.read(savable, database);
    }

    public boolean connect() {
        database = dbConnector.connect();
        if (database != null) {
            return true;
        }
        return false;
    }

    public void disconnect() {
        dbConnector.disconnect();
    }

}
