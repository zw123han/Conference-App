package Gateway;

import UserSystem.*;
import com.mongodb.*;

import java.util.ArrayList;

public class ParseToRegistrar implements ParserStrategy {

    private User createUser(String type, String name, String userName, String password, ArrayList<Long> events,
                            ArrayList<String> friends, Boolean vipStatus) {
        User user;
        if (type.equals("UserSystem.Attendee")) {
            user = new Attendee(name, userName, password);
        } else if (type.equals("UserSystem.Organizer")) {
            user = new Organizer(name, userName, password);
        } else if (type.equals("UserSystem.Administrator")) {
            user = new Administrator(name, userName, password);
        } else if (type.equals("UserSystem.Speaker")) {
            user = new Speaker(name, userName, password);
        } else {
            return null;
        }
        for (Long event : events) {
            user.addEvent(event);
        }
        for (String friend : friends) {
            user.addFriend(friend);
        }
        user.setVipStatus(vipStatus);

        return user;
    }

    private Speaker createSpeaker(String type, String name, String userName, String password, ArrayList<Long> events,
                                  ArrayList<String> friends, Boolean vipStatus, ArrayList<Long> talks) {
        Speaker user = (Speaker) createUser(type, name, userName, password, events, friends, vipStatus);
        for (Long talk: talks) {
            user.addTalk(talk);
        }
        return user;
    }

    @Override
    public Savable parseCollection(DBCollection collection) {
        ArrayList<User> users = new ArrayList<>();
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            if (doc.get("type").equals("UserSystem.Speaker")) {
                users.add(createSpeaker( (String) doc.get("type"),
                        (String) doc.get("name"),
                        (String) doc.get("userName"),
                        (String) doc.get("password"),
                        (ArrayList<Long>) doc.get("events"),
                        (ArrayList<String>) doc.get("friends"),
                        (Boolean) doc.get("vip"),
                        (ArrayList<Long>) doc.get("talks")));
            } else {
                users.add(createUser((String) doc.get("type"),
                        (String) doc.get("name"),
                        (String) doc.get("userName"),
                        (String) doc.get("password"),
                        (ArrayList<Long>) doc.get("events"),
                        (ArrayList<String>) doc.get("friends"),
                        (Boolean) doc.get("vip")));
            }

        }

        return new Registrar(users);
    }
}
