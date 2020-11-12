public class Main {

    public static void main(String[] arg){

        OrganizerCreationScript organizerCreationScript = new OrganizerCreationScript();
        organizerCreationScript.createOrganizers();

        ConferenceSimulator conference = new ConferenceSimulator();

        conference.run();

    }

}
