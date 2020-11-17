/**
 * An abstract presenter that contains the base features for all messaging-related presenters.
 *
 * @author Elliot
 */
public abstract class CommandPresenter {

    /**
     * Displays an exit command tip.
     */
    public void exitMessage() {
        System.out.println("$q to exit.");
    }

    /**
     * Displays message when field is invalid.
     *
     * @param field     field (user input)
     */
    public void invalidCommand(String field) {
        System.out.println("invalid " + field + ".");
    }

    /**
     * Displays prompt for user input into field.
     *
     * @param field     field (user input)
     */
    public void commandPrompt(String field) {
        System.out.println("Enter a valid " + field + ".");
        exitMessage();
    }
}
