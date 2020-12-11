import MainProgram.ConferenceSimulator;

/**
 * Main static method of program.
 *
 * @author Fred
 */
public class Main {

    /**
     * This is the main static method of the program.
     *
     * @param arg unused arguments. Here by convention.
     */
    public static void main(String[] arg){

        ConferenceSimulator conference = new ConferenceSimulator();

        conference.run();

    }

}
