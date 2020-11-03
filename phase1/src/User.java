import java.util.ArrayList;
import java.util.Objects;

public abstract class User{
    private String name, userName, hashPassword;

    private ArrayList<Objects> events;

    public User(String name, String userName, String hashPassword){
        this.name = name;
        this.userName = userName;
        this.hashPassword = hashPassword;
    }

    public User(String x, String y){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: password");
    }

    public User(String x){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: name, username or password");
    }

   public User(){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: name, user name and password");
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String getUserName(){ return userName; }

    public void setUserName(String userName){ this.userName = userName; }

    public String getPassword(){ return hashPassword; }

    public void setPassword(String hashPassword){ this.hashPassword = hashPassword; }

}

