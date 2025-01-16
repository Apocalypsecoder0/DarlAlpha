import java.util.ArrayList;
import java.util.List;

class Player {
    // Player class implementation (assuming it exists)
}

class Resources {
    // Resources class implementation (assuming it exists)
}

class AllianceShipyard {
    //Implementation of AllianceShipyard class
}


class Alliance {
    private String name;
    private Player leader;
    private List<Player> members;
    private Resources sharedResources;
    private AllianceShipyard shipyard;

    public Alliance(String name, Player leader) {
        this.name = name;
        this.leader = leader;
        this.members = new ArrayList<>();
        this.sharedResources = new Resources();
        this.shipyard = new AllianceShipyard();
        members.add(leader);
    }

    public AllianceShipyard getShipyard() {
        return shipyard;
    }
    //other methods of Alliance class
}