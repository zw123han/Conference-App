public class OrganizerCreationScript {

    public void createOrganizers(Registrar registrar) {
        CredentialsController credentialsController = new CredentialsController(registrar);
        credentialsController.createUser("Name", "Admin", "123", "Organizer");

        credentialsController.createUser("Name", "User", "123", "Organizer");

        // creates second organizer user with friend
        User user = registrar.getUserByUserName("User");
        FriendsManager fm = new FriendsManager(registrar);
        fm.addFriend(user, "Admin");

        //If you wish to create more organizers, use credentialsController.createUser


    }
}
