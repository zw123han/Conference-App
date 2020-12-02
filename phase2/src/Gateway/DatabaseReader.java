package Gateway;

import com.mongodb.DB;
import com.mongodb.DBCollection;

public class DatabaseReader {

    public Savable read(Savable sv, DB db) {
        ParserStrategy strategy = sv.getDocumentParserStrategy();
        DBCollection collection = db.getCollection(sv.getCollectionName());

        return strategy.parseCollection(collection);
    }

}
