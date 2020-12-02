package MessagingSystem;

import java.util.*;
import java.util.regex.*;

/**
 * An abstract presenter that contains the base features for all messaging-related presenters.
 *
 * @author Elliot, Chrisee
 */
public abstract class CommandPresenter {

    /**
     * Formats an exit command tip.
     *
     * @return  text for the exit command tip.
     */
    public String exitMessage() {
        return "$q to exit.";
    }

    /**
     * Formats prompt when field is invalid.
     *
     * @param field     field (user input)
     * @return         text display for invalid field prompt.
     */
    public String invalidCommand(String field) {
        return "invalid " + field + ".";
    }

    /**
     * Formats prompt for user input into field.
     *
     * @param field     field (user input)
     * @return         text display for field input prompt.
     */
    public String commandPrompt(String field) {
        return "Enter a valid " + field + ".\n" + exitMessage();
    }

    /**
     * Displays a success message for a sent message.
     *
     * @param recipient  username of the recipient
     * @return           text display alert for when a message is successfully sent.
     */
    public String success(String recipient) {
        return "\nMessage successfully sent to " + recipient + "!";
    }

}
