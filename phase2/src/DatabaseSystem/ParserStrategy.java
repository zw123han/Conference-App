package DatabaseSystem;

import com.mongodb.DBCollection;

public interface ParserStrategy {

    Savable parseCollection(DBCollection collection);

}
