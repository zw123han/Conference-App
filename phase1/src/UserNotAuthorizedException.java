public class UserNotAuthorizedException extends Exception {
    UserNotAuthorizedException(String message){
        super(message);
    }
    UserNotAuthorizedException(){
        super("Access Denied");
    }
}