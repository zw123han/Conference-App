/**
 * This class represents an Administrator with the highest levels of access. This user will be able to perform all the
 * same functions as an organizer, with added functionality to delete messages, events (where cancelled or with no
 * attendees)
 *
 * @author Fred
 */
public class Administrator extends User{

    /**
     * Constructor for the Admin class
     *
     * @param name          The name of the administrator
     * @param userName      The username of the administrator
     * @param password      The hashed password of the administrator
     */
    public Administrator(String name, String userName, String password){ super(name, userName, password); }

}
