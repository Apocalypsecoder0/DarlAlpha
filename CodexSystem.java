
import java.util.*;

public class CodexSystem {
    private Map<String, CodexCategory> categories;
    private Map<String, CodexEntry> entries;
    private Set<String> unlockedEntries;
    
    public CodexSystem() {
        this.categories = new HashMap<>();
        this.entries = new HashMap<>();
        this.unlockedEntries = new HashSet<>();
        initializeCategories();
    }
    
    private void initializeCategories() {
        addCategory("ships", "Starships", "Information about various spacecraft");
        addCategory("races", "Alien Races", "Known species in the universe");
        addCategory("tech", "Technology", "Scientific and technological discoveries");
        addCategory("planets", "Celestial Bodies", "Planets, moons, and other space objects");
        addCategory("history", "History", "Historical events and chronicles");
    }
    
    public void addCategory(String id, String name, String description) {
        categories.put(id, new CodexCategory(id, name, description));
    }
    
    public void addEntry(String categoryId, CodexEntry entry) {
        if (categories.containsKey(categoryId)) {
            entries.put(entry.getId(), entry);
            categories.get(categoryId).addEntry(entry.getId());
        }
    }
    
    public void unlockEntry(String entryId) {
        if (entries.containsKey(entryId)) {
            unlockedEntries.add(entryId);
        }
    }
    
    public boolean isEntryUnlocked(String entryId) {
        return unlockedEntries.contains(entryId);
    }
    
    public List<CodexEntry> getUnlockedEntries(String categoryId) {
        List<CodexEntry> categoryEntries = new ArrayList<>();
        if (categories.containsKey(categoryId)) {
            for (String entryId : categories.get(categoryId).getEntries()) {
                if (isEntryUnlocked(entryId)) {
                    categoryEntries.add(entries.get(entryId));
                }
            }
        }
        return categoryEntries;
    }
}

class CodexCategory {
    private String id;
    private String name;
    private String description;
    private List<String> entryIds;
    
    public CodexCategory(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.entryIds = new ArrayList<>();
    }
    
    public void addEntry(String entryId) {
        entryIds.add(entryId);
    }
    
    public List<String> getEntries() {
        return entryIds;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}

class CodexEntry {
    private String id;
    private String title;
    private String description;
    private String content;
    private Date discoveryDate;
    
    public CodexEntry(String id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.discoveryDate = new Date();
    }
    
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getContent() { return content; }
    public Date getDiscoveryDate() { return discoveryDate; }
}
