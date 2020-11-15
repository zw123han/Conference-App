/**
 * A class that should rarely change, with a single method that creates as many Organizer instances as needed for a
 * conference.
 *
 * @author Jesse
 * @version %I%, %G%
 */
public class OrganizerCreationScript {

    /**
     * Adds organizer objects to the conference's Registrar.
     *
     * @param registrar     the registrar containing all of the users attending the conference
     */
    public void createOrganizers(Registrar registrar) {
        CredentialsUseCase credentialsUseCase = new CredentialsUseCase(registrar);
        credentialsUseCase.createUser("Name", "Admin", "123", "Organizer");
        //If you wish to create more organizers, use credentialsController.createUser

    }
}
