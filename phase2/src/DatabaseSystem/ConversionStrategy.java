package DatabaseSystem;

import com.mongodb.BasicDBObject;
import java.util.ArrayList;

/**
 * This interface represents a strategy that converts a Savable object into a database collection.
 *
 * @author Jesse
 */
public interface ConversionStrategy {

    /**
     * Returns an array list of documents to be added to a collection based on the given Savable object.
     *
     * @param sv    The Savable to be converted.
     * @return      An array list of basic MongoDB documents.
     */
    ArrayList<BasicDBObject> convertAll(Savable sv);


}
