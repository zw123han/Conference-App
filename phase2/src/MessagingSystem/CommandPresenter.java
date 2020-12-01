package MessagingSystem;

import java.util.*;
import java.util.regex.*;

/**
 * An abstract presenter that contains the base features for all messaging-related presenters.
 *
 * @author Elliot, Chrisee
 */
public abstract class CommandPresenter {
    private boolean profane = false;
    private HashMap<String, String> profanities;

    // NOTE: keys of hashmap should be Regex! For example:
    // "fuck" = "(?i)f *u *c *k" to ignore case and whitespace
    // Use parseProfanity to do the conversion automatically when initializing profanities.
    private String parseProfanity(String profanity) {
        StringBuilder result = new StringBuilder("(?i)");
        String[] bad = profanity.split("");
        for (String letter : bad) {
            result.append(letter)
                    .append(" *");
        }
        return result.substring(0, result.length()-2);
    }

    public void changeProfanity(boolean setter) {
        profane = !profane;
    }

    private String newProfanityBuilder(String match, String profanity) {
        ArrayList<String> allFiller = new ArrayList<>();
        Pattern pattern = Pattern.compile("[ .]+");
        Matcher matcher = pattern.matcher(profanity);
        while (matcher.find()) {
            String filler = matcher.group();
            allFiller.add(filler);
        }
        return allFiller.get(0) + profanities.get(match) + allFiller.get(allFiller.size()-1);
    }

    private String matchProfanity(String match, String message) {
        String result = message;
        Pattern pattern = Pattern.compile("[ .]+" + match + "[ .]+");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String profanity = matcher.group();
            result = message.replaceFirst(profanity, newProfanityBuilder(match, profanity));
        }
        return result;
    }

    public String profanityFilter(String message) {
        for (String profanity : profanities.keySet()) {
            message = message.replace(profanity, profanities.get(profanity));
        }
        return message;
    }

    /**
     * Displays an exit command tip.
     */
    public String exitMessage() {
        return "$q to exit.";
    }

    /**
     * Displays message when field is invalid.
     *
     * @param field     field (user input)
     */
    public String invalidCommand(String field) {
        return "invalid " + field + ".";
    }

    /**
     * Displays prompt for user input into field.
     *
     * @param field     field (user input)
     */
    public String commandPrompt(String field) {
        return "Enter a valid " + field + ".\n" + exitMessage();
    }

    /**
     * Displays a success message for a sent message.
     *
     * @param recipient (enter message)
     */
    public String success(String recipient) {
        return "\nMessage successfully sent to " + recipient + "!";
    }

}
