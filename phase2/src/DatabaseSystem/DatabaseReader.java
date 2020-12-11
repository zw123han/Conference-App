package DatabaseSystem;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.HashMap;

/**
 * DatabaseReader contains methods that read from a database.
 *
 * @author Jesse
 */
public class DatabaseReader {

    /**
     * Returns a new Savable instance by reading from the given database using the strategy specified by the
     * Savable.
     *
     * @param sv    The Savable object you want updated and returned.
     * @param db    The database to read from.
     * @return      An updated Savable object.
     */
    public Savable read(Savable sv, DB db) {
        ParserStrategy strategy = sv.getDocumentParserStrategy();
        DBCollection collection = db.getCollection(sv.getCollectionName());

        return strategy.parseCollection(collection);
    }

    /**
     * Returns a hash map mapping profanity regex to their censored replacements from the given database.
     *
     * @param db    The database to read from.
     * @return      A hash map with String keys and String values.
     */
    public HashMap<String, String> getProfanityList(DB db) {
        HashMap<String, String> profanities = new HashMap<>();

        DBCollection collection = db.getCollection("profanities");
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            profanities.put((String) doc.get("regex"), (String) doc.get("replacement"));
        }

        return profanities;
    }

}
