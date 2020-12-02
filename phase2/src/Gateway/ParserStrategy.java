package Gateway;

import com.mongodb.DBCollection;

public interface ParserStrategy {

    public Savable parseCollection(DBCollection collection);

}
