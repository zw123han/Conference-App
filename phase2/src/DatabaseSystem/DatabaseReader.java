package DatabaseSystem;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.HashMap;

public class DatabaseReader {

    public Savable read(Savable sv, DB db) {
        ParserStrategy strategy = sv.getDocumentParserStrategy();
        DBCollection collection = db.getCollection(sv.getCollectionName());

        return strategy.parseCollection(collection);
    }

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
