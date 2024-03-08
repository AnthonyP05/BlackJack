public class BJCard {
    
    private String suit;
    private String value;

    public BJCard(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    // Getters and Setters


    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }

}
