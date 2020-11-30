package MessagingSystem;

public interface MessageControllerInterface {

    public boolean validateMessage(String message);

    public void sendMessage(String recipient, String message);
}
