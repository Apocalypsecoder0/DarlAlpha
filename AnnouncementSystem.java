
import java.util.*;

public class AnnouncementSystem {
    private List<Announcement> announcements;
    private Map<String, List<Announcement>> empireAnnouncements;

    public AnnouncementSystem() {
        this.announcements = new ArrayList<>();
        this.empireAnnouncements = new HashMap<>();
    }

    public void broadcastGlobal(String title, String message) {
        Announcement announcement = new Announcement(title, message, AnnouncementType.GLOBAL);
        announcements.add(announcement);
    }

    public void broadcastToEmpire(String empireId, String title, String message) {
        Announcement announcement = new Announcement(title, message, AnnouncementType.EMPIRE);
        empireAnnouncements.computeIfAbsent(empireId, k -> new ArrayList<>()).add(announcement);
    }

    public List<Announcement> getGlobalAnnouncements() {
        return new ArrayList<>(announcements);
    }

    public List<Announcement> getEmpireAnnouncements(String empireId) {
        return empireAnnouncements.getOrDefault(empireId, new ArrayList<>());
    }
}

class Announcement {
    private String title;
    private String message;
    private AnnouncementType type;
    private long timestamp;

    public Announcement(String title, String message, AnnouncementType type) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }
}

enum AnnouncementType {
    GLOBAL,
    EMPIRE
}
