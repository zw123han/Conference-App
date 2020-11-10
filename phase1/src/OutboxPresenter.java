import java.util.*;

public class OutboxPresenter {

    public OutboxPresenter() {
    }

    public void menuDisplay() {
        System.out.println("1 Direct message\n2 Group message");
    }

    public void invalidCommand(String field) {
        System.out.println(field + " not valid.");
    }

    public void commandPrompt(String field) {
        System.out.println("Enter a valid " + field + ".\n$back to exit");
    }

    public void success() {
        System.out.println("Message successfully sent!");
    }
}
