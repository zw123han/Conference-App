package DatabaseSystem;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.ArrayList;

/**
 * CollectionManager is responsible for inserting a Savable to its proper collection in the database.
 *
 * @author  Jesse
 */
public class CollectionManager {

    DB database;
    String collectionName;
    ConversionStrategy strategy;
    DBCollection collection;

    /**
     * Constructor for CollectionManager.
     *
     * @param database      The MongoDB database object to create/update collections to.
     * @param sv            The Savable object to be converted and then saved onto the database.
     */
    public CollectionManager(DB database, Savable sv) {
        this.database = database;
        collectionName = sv.getCollectionName();
        strategy = sv.getConversionStrategy();
        collection = database.getCollection(collectionName);
    }

    private void clearCollection() {
        collection.drop();
        collection = database.getCollection(collectionName);
    }

    /**
     * Updates the collection associated with the given Savable instance by dropping the collection in the database
     * and replacing it with a new one created from a list of MongoDB documents obtained by converting the Savable.
     *
     * @param sv    The Savable to be inserted.
     */
    public void insertToCollection(Savable sv) {
        clearCollection();
        ArrayList<BasicDBObject> documentList = strategy.convertAll(sv);
        for (BasicDBObject doc : documentList) {
            collection.insert(doc);
        }
    }
}
