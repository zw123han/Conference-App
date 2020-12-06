package DatabaseSystem;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.util.ArrayList;

public class DatabaseInteractor {

    DatabaseConnector dbConnector;
    DatabaseWriter dbWriter;
    DatabaseReader dbReader;
    MongoClient mongoClient;
    DB database;
    ArrayList<Savable> savables;

    public DatabaseInteractor() {
        dbConnector = new DatabaseConnector(mongoClient);
        dbWriter = new DatabaseWriter();
        dbReader = new DatabaseReader();
        savables = new ArrayList<>();
    }

    public ArrayList<Savable> getSavables() {
        return savables;
    }

    public void setSavables(ArrayList<Savable> savables) {
        this.savables = savables;
    }

    public void saveToDatabase() {
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
