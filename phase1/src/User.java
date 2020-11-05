import java.util.ArrayList;

public abstract class User{
    private String name, userName, hashPassword;

    private ArrayList<Integer> events;
    private ArrayList<User> friends;

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

    public void addEvent(Integer evt) {
        events.add(evt);
    }

    public void removeEvent(Event evt) { events.remove(evt); }

    public ArrayList<Event> getEvents() {
        return (ArrayList<Event>) events.clone(); //casted to avoid error. Is this correct?
    }

    public void addFriend(User friend) { friends.add(friend); }

    public void removeFriend(User friend) { friends.remove(friend); }

    public ArrayList<User> getFriends() { return (ArrayList<User>) friends.clone(); }

    public abstract boolean isAttendee();

    public abstract boolean isOrganizer();

    public abstract boolean isSpeaker();
}

