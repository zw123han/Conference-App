package MainProgram;

import EventSystem.*;
import GUISystem.*;
import LoginSystem.*;
import MessagingSystem.*;
import UserSystem.*;
import DatabaseSystem.*;
import com.mongodb.BasicDBObject;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.application.Application;

import java.util.*;

/**
 * This class rep qresents the executable for the application.
 *
 * @author  Fred, Ziwen
 */
public class ConferenceSimulator {
    DatabaseInteractor databaseInteractor = new DatabaseInteractor();

    /**
     * The static main method will run this method to start the application.
     */
    public void run() {
        // TODO: Add modify button to eventManager

        // Connect to database
        databaseInteractor.connect();

        // Build the conference application
        ConferenceBuilder conferenceBuilder = new ConferenceBuilder(databaseInteractor);


        conferenceBuilder.buildAConference();

        // Launch application
        Application.launch(LaunchMenu.class);

        // Save and disconnect from database
        databaseInteractor.saveToDatabase();
        System.out.println("Save successful");
        databaseInteractor.disconnect();

    }

}
