package DatabaseSystem;

public interface Savable {

    String getCollectionName();

    ConversionStrategy getConversionStrategy();

    ParserStrategy getDocumentParserStrategy();

}
