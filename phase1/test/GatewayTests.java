import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class GatewayTests {

    @Test
    public void storeUsersTest() {
        Registrar registrar = new Registrar();
        registrar.createUser("Bob", "Legend", "asdf", "Attendee");
        registrar.createUser("Rob", "Doughnut", "asdf", "Organizer");
        registrar.createUser("Tom", "Chrysler", "asdf", "Speaker");
        registrar.createUser("Billy", "Jaguar", "asdf", "Attendee");

        for (User user : registrar.getUsers()) {
            System.out.println(user.getName());
            System.out.println(user.getUserName());
            System.out.println(user.getPassword());
            System.out.println(user.getEvents());
        }
        StoreUsers storeUsers = new StoreUsers(registrar);
        storeUsers.store();
        // assert statements

    }

    @Test
    public void readUsersTest() {
        ReadUsers readUsers = new ReadUsers();
        ArrayList<User> users = readUsers.read();
        System.out.println(users);

        Registrar registrar = new Registrar(users);
        System.out.println(registrar.getUsers());

        for (User user : registrar.getUsers()) {
            System.out.println(user.getClass().getName());
            System.out.println(user.getName());
            System.out.println(user.getUserName());
            System.out.println(user.getPassword());
            System.out.println(user.getEvents());
        }
    }
}