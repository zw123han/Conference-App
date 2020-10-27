public class OrganizerTests {

    private boolean canLogIn(){
        //Can login.

        return false;
    }

    private boolean canCreateSpeakerAccounts(){
        //Can create speaker accounts.

        return false;
    }

    private boolean canCreateSpeakerSchedule(){
        //Can create speaker schedules.
        //Speakers can be assigned to one or more rooms at different times. At most 1 speaker at a time in each room.
        //Talks are 1 hour and between 9am - 5pm.
        //lock schedule for changes after start time. unlock after end time.

        return false;
    }

}
