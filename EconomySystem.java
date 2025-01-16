
public class EconomySystem {
    private Map<String, Double> resourcePrices;
    private Map<String, Integer> marketVolume;
    
    public EconomySystem() {
        this.resourcePrices = new HashMap<>();
        this.marketVolume = new HashMap<>();
        initializeBasePrices();
    }
    
    private void initializeBasePrices() {
        resourcePrices.put("Iron", 100.0);
        resourcePrices.put("Gold", 1000.0);
        resourcePrices.put("Crystal", 500.0);
        resourcePrices.put("Energy", 50.0);
    }
    
    public double getPrice(String resource) {
        return resourcePrices.getOrDefault(resource, 0.0);
    }
    
    public void updatePrice(String resource, double newPrice) {
        resourcePrices.put(resource, newPrice);
    }
    
    public void processTransaction(String resource, int amount) {
        marketVolume.merge(resource, amount, Integer::sum);
        adjustPriceBasedOnVolume(resource);
    }
    
    private void adjustPriceBasedOnVolume(String resource) {
        int volume = marketVolume.getOrDefault(resource, 0);
        double currentPrice = resourcePrices.get(resource);
        double adjustment = Math.log10(Math.abs(volume) + 1) * 0.01;
        
        if (volume > 0) {
            currentPrice *= (1 - adjustment);
        } else {
            currentPrice *= (1 + adjustment);
        }
        
        resourcePrices.put(resource, currentPrice);
    }
}
