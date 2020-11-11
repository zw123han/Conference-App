import java.util.*;

public abstract class CommandPresenter {
    public void exitMessage() {
        System.out.println("$q to exit.");
    }

    public void menuDisplay(ArrayList<String> options) {
        int n = 1;
        for (String option : options) {
            System.out.println(n + " " + option);
        }
        exitMessage();
    }

    public void invalidCommand(String field) {
        System.out.println(field + " not valid.");
    }

    public void commandPrompt(String field) {
        System.out.println("Enter a valid " + field);
        exitMessage();
    }
}
