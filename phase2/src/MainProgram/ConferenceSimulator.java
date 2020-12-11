package MainProgram;

import GUISystem.*;
import DatabaseSystem.*;
import javafx.application.Application;


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
