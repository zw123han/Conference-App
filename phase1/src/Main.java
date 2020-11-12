public class Main {

    public static void main(String[] arg){
        // Will overwrite all user data if run.
        // OrganizerCreationScript organizerCreationScript = new OrganizerCreationScript();
        // organizerCreationScript.createOrganizers();

        ConferenceSimulator conference = new ConferenceSimulator();

        conference.run();

    }

}
