package DatabaseSystem;

import com.mongodb.BasicDBObject;
import java.util.ArrayList;

public interface ConversionStrategy {

    ArrayList<BasicDBObject> convertAll(Savable sv);

}
