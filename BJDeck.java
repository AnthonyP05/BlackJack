import java.util.*;

public class BJDeck {
    private final List<BJCard> cards;

    public BJDeck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    public void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String value : values) {
                cards.add(new BJCard(suit, value));
            }
        }
    }

    public void shuffle() {
        // Shuffle the deck
        Collections.shuffle(cards);
    }

    public BJCard dealCard() {
        // Deal a card from the deck
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty. Cannot deal a card.");
        } else {
            return cards.remove(0);
        }
    }

    public int cardsLeft() {
        return cards.size();
    }

}
