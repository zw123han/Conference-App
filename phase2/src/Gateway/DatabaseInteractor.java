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
    ArrayList<Savable> savables;

    public DatabaseInteractor(ArrayList<Savable> savables) {
        this.savables = savables;
        dbConnector = new DatabaseConnector(mongoClient);
        dbWriter = new DatabaseWriter();
        dbReader = new DatabaseReader();
    }

    public void saveToDatabase(ArrayList<Savable> savables) {
        dbWriter.save(savables, database);
    }

    public ArrayList<Savable> readFromDatabase() {
        ArrayList<Savable> updatedSavables = new ArrayList<>();

        for (Savable savable : this.savables) {
            Savable updatedSavable = dbReader.read(savable, database);
            if (updatedSavable != null) {
                updatedSavables.add(updatedSavable);
            } else {
                updatedSavables.add(savable);
            }
        }
        this.savables = updatedSavables;
        return updatedSavables;
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
