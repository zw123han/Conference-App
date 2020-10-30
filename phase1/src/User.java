import java.util.ArrayList;

public abstract class User{
    private String name, userName = null;

    private char[] hashPassword = null;

    private ArrayList<String> events = null;

    public User(String name, String userName, char[] hashPassword){
        this.name = name;
        this.userName = userName;
        this.hashPassword = hashPassword;
    }

    public User(){
        //Work in Progress. Want to catch missing information and inform user. May be omitted in future release.
        System.out.println("Missing information: name, user name or password");
    }

}

