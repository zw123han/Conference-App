package DatabaseSystem;

import com.mongodb.DB;

import java.util.ArrayList;

public class DatabaseWriter {

    public void save(ArrayList<Savable> savables, DB db) {

        for (Savable sv : savables) {
            CollectionManager collectionManager = new CollectionManager(db, sv);
            collectionManager.insertToCollection(sv);
        }

    }

}
