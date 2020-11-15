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
        System.out.println(field + " not valid.");
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
