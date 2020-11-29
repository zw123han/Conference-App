package Gateway;

import UserSystem.Registrar;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.util.ArrayList;
import UserSystem.User;
import UserSystem.Speaker;
import EventSystem.Event;

public class RegistrarConverter implements ConversionStrategy {

    private BasicDBObject convertUser(User user) {
        BasicDBObject document = new BasicDBObject();
        document.put("name", user.getName());
        document.put("userName", user.getUserName());
        document.put("password", user.getPassword());
        BasicDBList events = new BasicDBList();
        for (Long evt : user.getEvents()) {
            events.add(evt);
        }
        document.put("events", events);
        BasicDBList friends = new BasicDBList();
        for (String friend : user.getFriends()) {
            friends.add(friend);
        }
        document.put("friends", friends);
        document.put("vip", user.getVipStatus());

        return document;
    }

    private BasicDBObject convertSpeaker(User user) {
        BasicDBObject document = convertUser(user);
        BasicDBList talks = new BasicDBList();
        for (Long evt : ((Speaker) user).getTalks()) {
            talks.add(evt);
        }
        document.put("talks", talks);
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
