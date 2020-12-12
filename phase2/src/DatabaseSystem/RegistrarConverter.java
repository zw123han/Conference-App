package DatabaseSystem;

import UserSystem.Registrar;
import com.mongodb.BasicDBObject;
import java.util.ArrayList;
import UserSystem.User;
import UserSystem.Speaker;

/**
 * This class is responsible for converting a Registrar object into a list of MongoDB documents to be added to a
 * collection in the database.
 *
 * @author Jesse
 */
public class RegistrarConverter implements ConversionStrategy {

    private BasicDBObject convertUser(User user) {
        BasicDBObject document = new BasicDBObject();
        document.put("type", user.getClass().getName());
        document.put("name", user.getName());
        document.put("userName", user.getUserName());
        document.put("password", user.getPassword());
        ArrayList<String> eventsList = new ArrayList<>();
        for (Long evtId : user.getEvents()) {
            eventsList.add(String.valueOf(evtId));
        }
        document.put("events", eventsList);
        document.put("friends", user.getFriends());
        document.put("vip", user.getVipStatus());

        return document;
    }

    private BasicDBObject convertSpeaker(User user) {
        BasicDBObject document = convertUser(user);
        document.put("talks", ((Speaker) user).getTalks());
        return document;
    }

    /**
     * Returns an array list of documents to be added to a collection based on the given Registrar object.
     *
     * @param sv    A Registrar object.
     * @return      A list of MongoDB documents.
     */
    @Override
    public ArrayList<BasicDBObject> convertAll(Savable sv) {
        Registrar registrar = (Registrar) sv;
        ArrayList<BasicDBObject> documentList = new ArrayList<>();
        for (User user : registrar.getUsers()) {
            if (user instanceof Speaker) {
                documentList.add(convertSpeaker(user));
            } else {
                documentList.add(convertUser(user));
            }
        }

        return documentList;
    }
}
