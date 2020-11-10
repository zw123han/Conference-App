public class UserNotFoundException extends Exception {
    UserNotFoundException(String message){
        super(message);
    }
    UserNotFoundException(){
        super("This user does not exist.");
    }
}
