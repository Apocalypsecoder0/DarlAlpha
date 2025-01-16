
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Integer, VaultTab> vaultTabs;
    private static final int MAX_STACK = 9801; // 99 x 99
    private static final int MAX_TABS = 5;

    public Bank() {
        this.vaultTabs = new HashMap<>();
        initializeTabs();
    }

    private void initializeTabs() {
        for (int i = 0; i < MAX_TABS; i++) {
            vaultTabs.put(i, new VaultTab());
        }
    }

    public boolean depositItem(BankItem item, int tabIndex) {
        if (tabIndex >= 0 && tabIndex < MAX_TABS) {
            return vaultTabs.get(tabIndex).addItem(item);
        }
        return false;
    }

    public BankItem withdrawItem(String itemId, int amount, int tabIndex) {
        if (tabIndex >= 0 && tabIndex < MAX_TABS) {
            return vaultTabs.get(tabIndex).removeItem(itemId, amount);
        }
        return null;
    }
}

class VaultTab {
    private Map<String, BankItem> items;
    
    public VaultTab() {
        this.items = new HashMap<>();
    }
    
    public boolean addItem(BankItem item) {
        String itemId = item.getId();
        if (items.containsKey(itemId)) {
            BankItem existingItem = items.get(itemId);
            if (existingItem.getAmount() + item.getAmount() <= 9801) {
                existingItem.addAmount(item.getAmount());
                return true;
            }
            return false;
        } else {
            items.put(itemId, item);
            return true;
        }
    }
    
    public BankItem removeItem(String itemId, int amount) {
        if (items.containsKey(itemId)) {
            BankItem item = items.get(itemId);
            if (item.getAmount() >= amount) {
                item.removeAmount(amount);
                if (item.getAmount() == 0) {
                    items.remove(itemId);
                }
                return new BankItem(itemId, amount);
            }
        }
        return null;
    }
}

class BankItem {
    private String id;
    private int amount;
    
    public BankItem(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }
    
    public String getId() { return id; }
    public int getAmount() { return amount; }
    public void addAmount(int add) { amount += add; }
    public void removeAmount(int remove) { amount -= remove; }
}
