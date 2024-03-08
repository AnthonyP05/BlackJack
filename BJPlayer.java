import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BJPlayer {
    private String name;
    private int bet;
    private List<BJCard> hand;
    private int score;

    public BJPlayer(String name, int bet) {
        this.name = name;
        this.bet = bet;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getBet() {
        return bet;
    }

    public void doubleBet() {
        this.bet *= 2;
    }

    public void addCardToHand(BJCard card) {
        // Add a card to the player's hand
        hand.add(card);
    }

    public List<BJCard> getHand() {
        return hand;
    }

    public String toString() {
        return name + ": " + hand;
    }

    public void removeFromHand() {

    }

    public boolean isBusted() {
        return calculateScore() > 21;
    }

    public int calculateScore() {
        // Calculate the total score of the player's hand
        score = 0;
        int numAces = 0;
        String[] arr = {"Jack", "Queen", "King", "Ace"};
        List<String> faces = Arrays.asList(arr);

        for(BJCard card : hand) {
            String cardValue = card.getValue();
            if (cardValue.equals("Ace")) {
                numAces++;
                score += 11;
            } else if (faces.contains(cardValue)) {
                score += 10;
            } else {
                score += Integer.parseInt(cardValue);
            }
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }
}
