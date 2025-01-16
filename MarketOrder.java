
public class MarketOrder {
    private String id;
    private String itemId;
    private double price;
    private int quantity;
    private String region;
    private OrderType type;
    private long expirationTime;
    private String contractorId;
    
    public enum OrderType {
        BUY, SELL, CONTRACT
    }
    
    public MarketOrder(String itemId, double price, int quantity, String region, OrderType type) {
        this.id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.region = region;
        this.type = type;
        this.expirationTime = System.currentTimeMillis() + (24 * 60 * 60 * 1000); // 24 hours
    }
    
    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getItemId() { return itemId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public String getRegion() { return region; }
    public OrderType getType() { return type; }
}
