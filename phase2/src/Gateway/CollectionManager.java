package Gateway;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.ArrayList;

public class CollectionManager {

    DB database;
    String collectionName;
    ConversionStrategy strategy;
    DBCollection collection;

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

    public void insertToCollection(Savable sv) {
        clearCollection();
        ArrayList<BasicDBObject> documentList = strategy.convertAll(sv);
        for (BasicDBObject doc : documentList) {
            collection.insert(doc);
        }
    }
}
