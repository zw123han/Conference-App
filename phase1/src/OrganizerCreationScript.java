/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class OrganizerCreationScript {

    /**
     * (please describe)
     *
     * @param registrar     (please describe)
     */
    public void createOrganizers(Registrar registrar) {
        CredentialsUseCase credentialsUseCase = new CredentialsUseCase(registrar);
        credentialsUseCase.createUser("Name", "Admin", "123", "Organizer");
        //If you wish to create more organizers, use credentialsController.createUser

//        StoreUsers storeUsers = new StoreUsers("phase1/src/userData.ser");
//        storeUsers.store(registrar.getUsers());

    }
}
