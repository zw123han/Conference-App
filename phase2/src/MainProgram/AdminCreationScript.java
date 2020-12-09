package MainProgram;

import UserSystem.CredentialsUseCase;
import UserSystem.Registrar;

/**
 * This script creates a default account for an Administer. This account holder will be responsible for the
 * designing and supporting the infrastructure.
 *
 * @author Fred
 */
public class AdminCreationScript {

    /**
     * Adds admin objects to the conference's Registrar.
     *
     * @param registrar     the registrar is responsible for registering users to the conference
     */
    public void createAdmin(Registrar registrar) {
        CredentialsUseCase credentialsUseCase = new CredentialsUseCase(registrar);
        credentialsUseCase.createUser("James Gosling", "admin", "123", "Administrator");
        credentialsUseCase.createUser("DO NOT MODIFY", "backup", "123", "Administrator");

    }
}
