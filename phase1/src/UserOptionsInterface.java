public class UserOptionsInterface {

    private LoginUI loginUI;
    // All other UIs go here too and in the constructor

    public UserOptionsInterface(LoginOptionsFacade loginFacade){
        this.loginUI = new LoginUI(loginFacade);
    }
    private void generalOptions(){
        // Check for input, then call appropriate private functions
    }

    public void showOptions(User user){
        if (user == null){
            login();
        }
        else if(user instanceof Attendee){

        }
        else if (user instanceof Organizer){

        }
        else if (user instanceof Speaker){

        }
    }

    private void login(){
        loginUI.login();
    }
    private void logout(){
        loginUI.logout();
    }
    private void changePassword(){
        loginUI.changePassword();
    }
}
