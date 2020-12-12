package DatabaseSystem;

/**
 * This interface represents an object in this program that can and should be stored on the database after program end.
 * As such, any object that implements this interface should specify where it should be stored, how it is stored, and
 * how it can be retrieved.
 *
 * @author Jesse
 */
public interface Savable {

    /**
     * Returns the name of the collection (the location) at which the current Savable should be stored on the database.
     *
     * @return      String representing the name of the collection.
     */
    String getCollectionName();

    /**
     * Returns the method that should be used when storing the current Savable into the database.
     *
     * @return      A ConversionStrategy instance representing the method of storage.
     */
    ConversionStrategy getConversionStrategy();

    /**
     * Returns the method that should be used when retrieving information from a database in order to reconstruct the
     * current Savable.
     *
     * @return      A ParserStrategy instance representing the method of retrieval.
     */
    ParserStrategy getDocumentParserStrategy();

}
