
public enum Major_Powers_Race {
    FEDERATION("United Federation of Planets", "Democratic", 1.2, 1.1, 1.0),
    KLINGON("Klingon Empire", "Warrior", 1.0, 1.3, 0.9),
    ROMULAN("Romulan Star Empire", "Cunning", 1.1, 1.0, 1.2),
    CARDASSIAN("Cardassian Union", "Military", 0.9, 1.2, 1.1),
    FERENGI("Ferengi Alliance", "Commercial", 0.8, 0.7, 1.5),
    DOMINION("The Dominion", "Order", 1.3, 1.2, 0.8),
    BORG("Borg Collective", "Hivemind", 1.4, 1.4, 0.5);

    private final String empireName;
    private final String trait;
    private final double techMultiplier;
    private final double combatMultiplier;
    private final double tradeMultiplier;

    Major_Powers_Race(String empireName, String trait, double techMultiplier, 
                     double combatMultiplier, double tradeMultiplier) {
        this.empireName = empireName;
        this.trait = trait;
        this.techMultiplier = techMultiplier;
        this.combatMultiplier = combatMultiplier;
        this.tradeMultiplier = tradeMultiplier;
    }

    public String getEmpireName() { return empireName; }
    public String getTrait() { return trait; }
    public double getTechMultiplier() { return techMultiplier; }
    public double getCombatMultiplier() { return combatMultiplier; }
    public double getTradeMultiplier() { return tradeMultiplier; }
}
