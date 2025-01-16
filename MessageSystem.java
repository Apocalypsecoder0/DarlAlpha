import java.util.*;

public class MessageSystem {
    private Map<String, List<Message>> playerInboxes;
    private Map<String, List<Message>> playerSentMessages;
    private Map<String, List<Message>> allianceChats;
    private List<Message> universeChat;
    private Map<String, List<Message>> zoneChats;
    private boolean isOfflineMode = false;
    private Queue<String> messages;

    public MessageSystem() {
        this.playerInboxes = new HashMap<>();
        this.playerSentMessages = new HashMap<>();
        this.allianceChats = new HashMap<>();
        this.universeChat = new ArrayList<>();
        this.zoneChats = new HashMap<>();
        this.messages = new LinkedList<>();
    }

    public void sendPrivateMessage(String sender, String recipient, String content) {
        Message message = new Message(sender, recipient, "PRIVATE", content);
        playerInboxes.computeIfAbsent(recipient, k -> new ArrayList<>()).add(message);
        playerSentMessages.computeIfAbsent(sender, k -> new ArrayList<>()).add(message);
    }

    public void sendAllianceMessage(String sender, String allianceId, String content) {
        if (isOfflineMode) return;
        Message message = new Message(sender, allianceId, "ALLIANCE", content);
        allianceChats.computeIfAbsent(allianceId, k -> new ArrayList<>()).add(message);
    }

    public void sendUniverseMessage(String sender, String content) {
        if (isOfflineMode) return;
        Message message = new Message(sender, "UNIVERSE", "UNIVERSE", content);
        universeChat.add(message);
    }

    public void sendZoneMessage(String sender, String zoneId, String content) {
        if (isOfflineMode) return;
        Message message = new Message(sender, zoneId, "ZONE", content);
        zoneChats.computeIfAbsent(zoneId, k -> new ArrayList<>()).add(message);
    }

    public List<Message> getAllianceMessages(String allianceId) {
        return allianceChats.getOrDefault(allianceId, new ArrayList<>());
    }

    public List<Message> getUniverseMessages() {
        return new ArrayList<>(universeChat);
    }

    public List<Message> getZoneMessages(String zoneId) {
        return zoneChats.getOrDefault(zoneId, new ArrayList<>());
    }

    public void setOfflineMode(boolean offline) {
        this.isOfflineMode = offline;
    }

    public boolean isOfflineMode() {
        return isOfflineMode;
    }

    public void addMessage(String message) {
        messages.offer(message);
    }

    public String getNextMessage() {
        return messages.poll();
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}