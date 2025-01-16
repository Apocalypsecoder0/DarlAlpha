
import java.util.Date;

public class Message {
    private String sender;
    private String recipient;
    private String type;
    private String content;
    private Date timestamp;
    private long id;
    private static long nextId = 0;

    public Message(String sender, String recipient, String type, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.type = type;
        this.content = content;
        this.timestamp = new Date();
        this.id = nextId++;
    }

    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getType() { return type; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
    public long getId() { return id; }
}
