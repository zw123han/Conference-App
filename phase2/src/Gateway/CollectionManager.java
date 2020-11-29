package Gateway;

import EventSystem.EventManager;
import MessagingSystem.ChatroomManager;
import UserSystem.Registrar;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.ArrayList;

public class CollectionManager {

    DB database;
    String collectionName;
    ConversionStrategy strategy;
    DBCollection collection;

    public CollectionManager(DB database, String collectionName, ConversionStrategy strategy) {
        this.database = database;
        this.collectionName = collectionName;
        this.strategy = strategy;
        collection = database.getCollection(collectionName);
    }

    private ConversionStrategy insertable(Savable sv) {
        if (sv instanceof Registrar && strategy instanceof RegistrarConverter) {
            return new RegistrarConverter();
        } else if (sv instanceof EventManager && strategy instanceof EventManagerConverter) {
            return new EventManagerConverter();
        } else if (sv instanceof ChatroomManager && strategy instanceof ChatroomManagerConverter) {
            return new ChatroomManagerConverter();
        }
        return null;
    }

    private void clearCollection() {
        collection.drop();
        collection = database.getCollection(collectionName);
    }

    public boolean insertToCollection(Savable sv) {
        if (insertable(sv) != null) {
            clearCollection();
            ConversionStrategy converter = insertable(sv);
            ArrayList<BasicDBObject> documentList = converter.convertAll(sv);
            for (BasicDBObject doc : documentList) {
                collection.insert(doc);
            }
            return true;
        } else {
            return false;
        }
    }

}
