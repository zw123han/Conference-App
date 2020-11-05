import java.util.ArrayList;

public abstract class User{
    private String name, userName, hashPassword;

    private ArrayList<Integer> events;
    private ArrayList<String> friends;

    public User(String name, String userName, String hashPassword){
        this.name = name;
        this.userName = userName;
        this.hashPassword = hashPassword;
    }

    public User(){
        //Work in Progress. May be omitted in future.
        System.out.println("Error. Missing: name, user name and password");
    }

    public String getName(){ return name; }

    public String getUserName(){ return userName; }

    public String getPassword(){ return hashPassword; }

    public void setPassword(String hashPassword){ this.hashPassword = hashPassword; }

    public void addEvent(Integer evt) { events.add(evt); }

    public void removeEvent(Integer evt) { events.remove(evt); }

    public ArrayList<Integer> getEvents() {
        return (ArrayList<Integer>) events.clone(); //casted to avoid error. Is this correct?
    }

    public void addFriend(String friend) { friends.add(friend); }

    public void removeFriend(String friend) { friends.remove(friend); }

    public ArrayList<String> getFriends() { return (ArrayList<String>) friends.clone(); }

}

