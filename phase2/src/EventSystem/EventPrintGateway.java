package EventSystem;

import java.io.*;
import UserSystem.User;

public class EventPrintGateway {

    public void print(User user) throws FileNotFoundException {
    //EventSignupPresenter ecp = new EventSignupPresenter();

        PrintStream doc = new PrintStream(new File("events.txt"));

        System.setOut(doc);


    }
}
