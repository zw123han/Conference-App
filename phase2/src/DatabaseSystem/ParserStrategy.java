package DatabaseSystem;

import com.mongodb.DBCollection;

/**
 * This interface represents a strategy that converts a database collection into a Savable object.
 *
 * @author Jesse
 */
public interface ParserStrategy {

    /**
     * Returns a new Savable instance based on the given collection.
     *
     * @param collection    The collection that stores information for the Savable.
     * @return              A new Savable instance.
     */
    Savable parseCollection(DBCollection collection);

}
