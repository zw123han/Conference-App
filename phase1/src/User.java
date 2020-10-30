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
        System.out.println("Information needed: name, user name and password");
    }

}

