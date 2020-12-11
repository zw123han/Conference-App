package DatabaseSystem;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DatabaseInteractor is a facade class responsible for redirecting any interactions with the database to the
 * appropriate classes.
 *
 * @author Jesse
 */
public class DatabaseInteractor {

    DatabaseConnector dbConnector;
    DatabaseWriter dbWriter;
    DatabaseReader dbReader;
    MongoClient mongoClient;
    DB database;
    ArrayList<Savable> savables;

    /**
     * Empty constructor that initializes all of the necessary classes. Note that mongoClient does not need to be
     * initialized since DatabaseConnector will take care of that.
     */
    public DatabaseInteractor() {
        dbConnector = new DatabaseConnector(mongoClient);
        dbWriter = new DatabaseWriter();
        dbReader = new DatabaseReader();
        savables = new ArrayList<>();
    }

    /**
     * Returns an array list of Savable objects that are being staged for save.
     *
     * @return      An arraylist of Savable instances.
     */
    public ArrayList<Savable> getSavables() {
        return savables;
    }

    /**
     * Setter for the list of Savable objects to be staged.
     *
     * @param savables      The arraylist of Savables to be set.
     */
    public void setSavables(ArrayList<Savable> savables) {
        this.savables = savables;
    }

    /**
     * Saves the staged list of Savable objects to the database.
     */
    public void saveToDatabase() {
        dbWriter.save(savables, database);
    }

    /**
     * Returns a new instance of Savable constructed by retrieving information from the database. The new Savable is
     * of the same type as the given Savable.
     *
     * @param savable       The Savable object to be updated.
     * @return              A new instance of that Savable after retrieval from the database.
     */
    public Savable readFromDatabase(Savable savable) {
        return dbReader.read(savable, database);
    }

    /**
     * Returns a hash map mapping profanity regex to their censored replacements from the database.
     *
     * @return      A hash map with String keys and String values.
     */
    public HashMap<String, String> getProfanityList() {
        return dbReader.getProfanityList(database);
    }

    /**
     * Connects to the database and initializes the database variable.
     */
    public void connect() {
        database = dbConnector.connect();
    }

    /**
     * Disconnects from the database.
     */
    public void disconnect() {
        dbConnector.disconnect();
    }

}
