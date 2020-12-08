package MainProgram;

import UserSystem.CredentialsUseCase;
import UserSystem.Registrar;

/**
 * A class that should rarely change, with a single method that creates as many Organizer instances as needed for a
 * conference.
 *
 * @author Jesse, Fred
 */
public class OrganizerCreationScript {

    /**
     * Adds organizer objects to the conference's Registrar.
     *
     * @param registrar     the registrar containing all of the users attending the conference
     */
    public void createOrganizers(Registrar registrar) {
        CredentialsUseCase credentialsUseCase = new CredentialsUseCase(registrar);
        credentialsUseCase.createUser("Richard Saul Wurman", "boss1", "123", "Organizer");
        credentialsUseCase.createUser("Harry Marks", "boss2", "123", "Organizer");

        //If you wish to create more organizers, use credentialsController.createUser

    }
}
