package DatabaseSystem;

import com.mongodb.DB;

import java.util.ArrayList;

/**
 * DatabaseWriter contains methods that save to a database.
 *
 * @author Jesse
 */
public class DatabaseWriter {

    /**
     * Saves a list of Savable objects to the given database.
     *
     * @param savables      A list of Savable objects to be saved.
     * @param db            The database to save to.
     */
    public void save(ArrayList<Savable> savables, DB db) {
        for (Savable sv : savables) {
            CollectionManager collectionManager = new CollectionManager(db, sv);
            collectionManager.insertToCollection(sv);
        }

    }

}
