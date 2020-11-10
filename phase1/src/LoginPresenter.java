public class LoginPresenter {
    public String notLoggedInOptions(){
        return "You are not currently logged in. Press Y to login, or any other key to exit";
    }
    public String loggedInOptions(){
        return "Press L to logout. Press C to change password. Press any other key to show home screen.";
    }
    public String inquireName(){
        return "What is your name?";
    }
    public String inquireExistingAccount(){
        return "Do you already have an account registered? (Y/N). Default N.";
    }
    public String promptAccountCreation(String name) {
        return "Welcome to the conference " + name + "! Please create an account to continue.";
    }
    public String promptAccountUsername(){
        return "Enter username:";}

    public String promptAccountPassword(){
        return "Enter password:";}

    public String promptAccountLogin(String name){
        return "Welcome back "+name+". Please enter your username and password to continue.";
    }
    public String passwordUpdated(){
        return "Password updated.";
    }
    public String loggedOff(){
        return "Successfully logged out.";
    }
    public String noSuchPerson() {
        return "We're sorry, you're not registered at this conference.";
    }
    public String usernameTaken() {
        return "That username is taken. Please try again.";
    }
    public String failedLogin() {
        return "We're sorry, credentials incorrect.";
    }
    public String successfulLogin(){
        return "Successfully logged in.";
    }
    public String userCreated() {
        return "Account created. Please log in.";
    }

}