package MainProgram;

import EventSystem.EventManager;
import EventSystem.ReadEvents;
import EventSystem.SaveEvents;
import MessagingSystem.*;
import UserSystem.ReadUsers;
import UserSystem.Registrar;
import UserSystem.StoreUsers;

import java.util.HashMap;

public class LocalSave {

    private String userFilepath;
    private String eventFilepath;
    private String chatFilepath;
    private String profanityFilepath;

    private ReadEvents readEvents;
    private ReadUsers readUsers;
    private ReadChat readChat;
    private ReadProfanityList readProfanityList;

    private SaveEvents saveEvents;
    private StoreUsers storeUsers;
    private StoreChat storeChat;

    private Registrar registrar;
    private EventManager eventManager;
    private ChatroomManager chatroomManager;
    private HashMap<String, String> profanities;

    public LocalSave(String userFilepath, String eventFilepath, String chatFilepath, String profanityFilepath){
        this.userFilepath = userFilepath;
        this.eventFilepath = eventFilepath;
        this.chatFilepath = chatFilepath;
        this.profanityFilepath = profanityFilepath;
        setGateways();
        setUseCases();


    }

    private void setGateways(){
        this.readEvents = new ReadEvents(eventFilepath);
        this.readUsers = new ReadUsers(userFilepath);
        this.readChat = new ReadChat(chatFilepath);
        this.readProfanityList = new ReadProfanityList(profanityFilepath);

        this.saveEvents = new SaveEvents(eventFilepath);
        this.storeUsers = new StoreUsers(userFilepath);
        this.storeChat = new StoreChat(chatFilepath);
    }

    private void setUseCases(){
        this.registrar = new Registrar(readUsers.read());
        this.eventManager = new EventManager(readEvents.read());
        this.chatroomManager = readChat.readChatlog();
        this.profanities = readProfanityList.readProfanities();
    }

    public Registrar getRegistrar(){
        return registrar;
    }
    public  EventManager getEventManager(){
        return eventManager;
    }

    public ChatroomManager getChatroomManager(){
        return chatroomManager;
    }

    public HashMap<String, String> getProfanities(){
        return profanities;
    }

    public void save(){
        saveEvents.saveEvents(eventManager.getEventsList());
        storeUsers.store(registrar.getUsers());
        storeChat.storeChat(chatroomManager);
    }
}
