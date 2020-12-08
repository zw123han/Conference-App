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
 * This class represents the executable for the application.
 *
 * @author  Fred, Ziwen
 */
public class ConferenceSimulator {
    // For database saving
    DatabaseInteractor databaseInteractor = new DatabaseInteractor();
    // For local saving, must modify the following classes by following appropriate comments
    // ConferenceSimulator
    // ConferenceBuilder
    // HomeMenuGUI

    /**
     * The static main method will run this method to start the application.
     */
    public void run() {
        // TODO: outbox menus - Elliot
        // TODO: implement updateUsername in ManageAccountMenu - William
        // TODO: let event ids be copiable in ManageEventMenu and EventMenu - William

        // Connect to database
        databaseInteractor.connect();

        // Build the conference application
        // For database saving
        ConferenceBuilder conferenceBuilder = new ConferenceBuilder(databaseInteractor);

        // For local saving
        // String userFilepath = "phase2/src/UserSystem/userData.ser";
        // String eventFilepath = "phase2/src/EventSystem/eventData.ser";
        // String chatFilepath = "phase2/src/MessagingSystem/chatlog.ser";
        // String profanityListFilepath = "phase2/src/MessagingSystem/profanityList.ser";

        // MainProgram.LocalSave localSave = new MainProgram.LocalSave(userFilepath, eventFilepath, chatFilepath, profanityListFilepath);
        // MainProgram.ConferenceBuilder conferenceBuilder = new MainProgram.ConferenceBuilder(localSave);

        conferenceBuilder.buildAConference();

        // Launch application
        Application.launch(LaunchMenu.class);

        // Save and disconnect from database
        databaseInteractor.disconnect();

    }

}
