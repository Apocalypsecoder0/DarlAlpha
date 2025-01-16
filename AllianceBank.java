import java.util.*;

public class AllianceBank extends Bank {
    private String allianceId;
    private Map<String, BankPermission> memberPermissions;

    public AllianceBank(String allianceId) {
        super();
        this.allianceId = allianceId;
        this.memberPermissions = new HashMap<>();
    }

    public void setMemberPermission(String playerId, BankPermission permission) {
        memberPermissions.put(playerId, permission);
    }

    public boolean canAccess(String playerId, BankAction action) {
        BankPermission permission = memberPermissions.get(playerId);
        if (permission == null) return false;
        return permission.hasPermission(action);
    }
}

enum BankAction {
    DEPOSIT,
    WITHDRAW,
    VIEW
}

class BankPermission {
    private Set<BankAction> allowedActions;

    public BankPermission() {
        this.allowedActions = new HashSet<>();
    }

    public void addPermission(BankAction action) {
        allowedActions.add(action);
    }

    public boolean hasPermission(BankAction action) {
        return allowedActions.contains(action);
    }
}