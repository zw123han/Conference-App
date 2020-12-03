package DatabaseSystem;

import UserSystem.*;
import com.mongodb.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ParseToRegistrar implements ParserStrategy {

    private User createUser(DBObject doc) {
        User user;
        Class partypes[] = new Class[3];
        partypes[0] = String.class;
        partypes[1] = String.class;
        partypes[2] = String.class;
        try {
            user = (User) (Class.forName((String) doc.get("type")).getConstructor(partypes).newInstance(
                    doc.get("name"), doc.get("userName"), doc.get("password")));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

        for (Long event : (ArrayList<Long>) doc.get("events")) {
            user.addEvent(event);
        }
        for (String friend : (ArrayList<String>) doc.get("friends")) {
            user.addFriend(friend);
        }
        user.setVipStatus((Boolean) doc.get("vip"));

        return user;
    }

    private Speaker createSpeaker(DBObject doc) {
        Speaker user = (Speaker) createUser(doc);
        for (Long talk: (ArrayList<Long>) doc.get("talks")) {
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
                users.add(createSpeaker(doc));
            } else {
                users.add(createUser(doc));
            }

        }

        return new Registrar(users);
    }
}
