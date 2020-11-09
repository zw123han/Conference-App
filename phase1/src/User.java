import java.util.ArrayList;

public abstract class User{
    private String name, userName, hashPassword;

    private ArrayList<Long> events;
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

    public void addEvent(Long evt) { events.add(evt); }

    public void removeEvent(Long evt) { events.remove(evt); }

    public ArrayList<Long> getEvents() {
        return (ArrayList<Long>) events.clone(); //casted to avoid error. Is this correct?
    }

    public void addFriend(String friend) { friends.add(friend); }

    public void removeFriend(String friend) { friends.remove(friend); }

    public ArrayList<String> getFriends() { return (ArrayList<String>) friends.clone(); }

    public boolean hasFriend(String friend) {
        return friends.contains(friend);
    }

    public boolean isUser(User user) {
        return userName.equals(user.getUserName());
    }

}

