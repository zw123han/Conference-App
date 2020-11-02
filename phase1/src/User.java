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

    public User(String name, String userName){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: password");
    }

    public User(String name){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: name/password and password");
    }

    public User(String name, char[] hashPassword){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: name/user name");
    }

    public User(char[] hashPassword){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: name and user name");
    }

    public User(){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: name, user name and password");
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String getUserName(){ return userName; }

    public void setUserName(String userName){ this.userName = userName; }

    public char[] getPassword(){ return hashPassword; }

    public void setPassword(char[] hashPassword){ this.hashPassword = hashPassword; }

}

