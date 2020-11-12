public class OrganizerCreationScript {

    public void createOrganizers(Registrar registrar) {
        CredentialsUseCase credentialsUseCase = new CredentialsUseCase(registrar);
        credentialsUseCase.createUser("Name", "Admin", "123", "Organizer");

        credentialsUseCase.createUser("Name", "User", "123", "Organizer");

        // creates second organizer user with friend
        User user = registrar.getUserByUserName("User");
        FriendsManager fm = new FriendsManager(registrar);
        fm.addFriend(user, "Admin");

        //If you wish to create more organizers, use credentialsController.createUser


    }
}
