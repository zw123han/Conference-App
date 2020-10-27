public abstract class UserFacade {
    public String userLogon = null;

    private String firstName, lastName, userEmail = null;

    private String userSID = null; //Security identifier of users.

    private char[] userPassword = null;

    //private int accountType = 1; //0 = Administrator; 1 = Standard User. 3 = Guests

}
