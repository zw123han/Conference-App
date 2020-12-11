package DatabaseSystem;

import com.mongodb.BasicDBObject;
import java.util.ArrayList;

public interface ConversionStrategy {

    public ArrayList<BasicDBObject> convertAll(Savable sv);

}
