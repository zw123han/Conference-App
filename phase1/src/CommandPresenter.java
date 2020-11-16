/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public abstract class CommandPresenter {

    /**
     * (please describe)
     */
    public void exitMessage() {
        System.out.println("$q to exit.");
    }

    /**
     * (please describe)
     *
     * @param field     (please describe)
     */
    public void invalidCommand(String field) {
        System.out.println("invalid " + field + ".");
    }

    /**
     * (please describe)
     *
     * @param field     (please describe)
     */
    public void commandPrompt(String field) {
        System.out.println("Enter a valid " + field + ".");
        exitMessage();
    }
}
