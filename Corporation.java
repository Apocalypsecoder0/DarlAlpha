
import java.util.*;

public class Corporation {
    private String id;
    private String name;
    private Map<String, CorporateRole> roles;
    private Map<String, Member> members;
    private Map<String, Territory> controlledTerritories;
    private CorporateAssets assets;
    private TaxSystem taxSystem;
    
    public Corporation(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.roles = new HashMap<>();
        this.members = new HashMap<>();
        this.controlledTerritories = new HashMap<>();
        this.assets = new CorporateAssets();
        this.taxSystem = new TaxSystem();
        initializeDefaultRoles();
    }
    
    private void initializeDefaultRoles() {
        roles.put("CEO", new CorporateRole("CEO", true, true, true, true));
        roles.put("Director", new CorporateRole("Director", true, true, true, false));
        roles.put("Manager", new CorporateRole("Manager", true, true, false, false));
        roles.put("Member", new CorporateRole("Member", false, false, false, false));
    }
    
    public void addMember(String playerId, String roleId) {
        members.put(playerId, new Member(playerId, roles.get(roleId)));
    }
    
    public boolean hasPermission(String playerId, Permission permission) {
        Member member = members.get(playerId);
        return member != null && member.getRole().hasPermission(permission);
    }
}

class CorporateRole {
    private String name;
    private boolean canManageRoles;
    private boolean canManageAssets;
    private boolean canManageTerritories;
    private boolean canSetTaxes;
    
    public CorporateRole(String name, boolean canManageRoles, boolean canManageAssets, 
                        boolean canManageTerritories, boolean canSetTaxes) {
        this.name = name;
        this.canManageRoles = canManageRoles;
        this.canManageAssets = canManageAssets;
        this.canManageTerritories = canManageTerritories;
        this.canSetTaxes = canSetTaxes;
    }
    
    public boolean hasPermission(Permission permission) {
        switch(permission) {
            case MANAGE_ROLES: return canManageRoles;
            case MANAGE_ASSETS: return canManageAssets;
            case MANAGE_TERRITORIES: return canManageTerritories;
            case SET_TAXES: return canSetTaxes;
            default: return false;
        }
    }
}
