public class EmptyUserException extends Exception {
    EmptyUserException(){
        super("No user logged in.");
    }
}
