public class OrganizerCreationScript {

    public void createOrganizers() {
        Registrar registrar = new Registrar();
        CredentialsController credentialsController = new CredentialsController(registrar);
        credentialsController.createUser("Name", "Admin", "123", "Organizer");
        //If you wish to create more organizers, use credentialsController.createUser

        StoreUsers storeUsers = new StoreUsers("phase1/src/userData.ser");
        storeUsers.store(registrar.getUsers());
    }
}
