package Gateway;

import UserSystem.Registrar;
import com.mongodb.BasicDBObject;
import java.util.ArrayList;
import UserSystem.User;
import UserSystem.Speaker;

public class RegistrarConverter implements ConversionStrategy {

    private BasicDBObject convertUser(User user) {
        BasicDBObject document = new BasicDBObject();
        document.put("type", user.getClass().getName());
        document.put("name", user.getName());
        document.put("userName", user.getUserName());
        document.put("password", user.getPassword());
        document.put("events", user.getEvents());
        document.put("friends", user.getFriends());
        document.put("vip", user.getVipStatus());

        return document;
    }

    private BasicDBObject convertSpeaker(User user) {
        BasicDBObject document = convertUser(user);
        document.put("talks", ((Speaker) user).getTalks());
        return document;
    }

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
