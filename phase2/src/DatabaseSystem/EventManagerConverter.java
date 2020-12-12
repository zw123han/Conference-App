package DatabaseSystem;

import EventSystem.Event;
import EventSystem.EventManager;
import com.mongodb.BasicDBObject;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class is responsible for converting a EventManager object into a list of MongoDB documents to be added to a
 * collection in the database.
 *
 * @author Jesse
 */
public class EventManagerConverter implements ConversionStrategy {

    private BasicDBObject convertEvent(Event evt) {
        BasicDBObject document = new BasicDBObject();
        document.put("id", String.valueOf(evt.getId()));
        document.put("name", evt.getName());
        document.put("room", evt.getRoom());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        document.put("time", evt.getTime().format(formatter));
        document.put("speaker", evt.getSpeakerList());
        document.put("duration", String.valueOf(evt.getDuration()));
        document.put("capacity", evt.getCapacity());
        document.put("signedUp", evt.getSignedUpUsers());

        return document;
    }

    /**
     * Returns an array list of documents to be added to a collection based on the given EventManager object.
     *
     * @param sv    A EventManager object.
     * @return      A list of MongoDB documents.
     */
    @Override
    public ArrayList<BasicDBObject> convertAll(Savable sv) {
        EventManager eventManager = (EventManager) sv;
        ArrayList<BasicDBObject> documentList = new ArrayList<>();
        for (Event evt : eventManager.getEventsList()) {
            documentList.add(convertEvent(evt));
        }

        return documentList;
    }
}
