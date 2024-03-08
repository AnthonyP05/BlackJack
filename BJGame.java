import java.util.*;

public class BJGame {

    private final BJDeck deck;
    HashMap<BJPlayer, Integer> playerx;

    private final BJPlayer dealer;
    Scanner keyboard = new Scanner(System.in);

    public BJGame() {
        this.deck = new BJDeck();
        this.playerx = new HashMap<>();
        this.dealer = new BJPlayer("Dealer", 0);
    }

    public void startGame() {
        boolean playAgain = true;

        while (playAgain) {
            // Initialize the game, deal cards to players and dealer
            System.out.println("Hello! How many players are playing today?");
            int playerAmount = keyboard.nextInt();

            for(int i = 0; i < playerAmount; i++) {
                System.out.printf("Player %d name? ", i + 1);
                String name = keyboard.next();
                System.out.printf("Player %d bet? ", i + 1);
                int bet = keyboard.nextInt();
                BJPlayer player = new BJPlayer(name, bet);
                playerx.put(player, player.getBet());
            }

            System.out.println("\nAlright let's begin! Dealer is up first.\n");

            BJCard dealerUpCard = deck.dealCard();
            System.out.println("Dealer drew ? card, and a " + dealerUpCard.toString());

            // Each Player is given 2 cards at the start
            for (int i = 0; i < 2; i++) {
                for (Map.Entry<BJPlayer, Integer> player : playerx.entrySet()) {
                    BJCard card = deck.dealCard();
                    player.getKey().addCardToHand(card);
                    System.out.printf("%s was given " + card.toString() + "\n", player.getKey().getName());
                }
            }

            // Dealer is given 2 cards at the start, one face up and one faced down.
            dealer.addCardToHand(deck.dealCard());
            dealer.addCardToHand(dealerUpCard);

            boolean firstTurn = true;
            boolean gameOver = false;

            while (!gameOver) {
                for (Map.Entry<BJPlayer, Integer> player : playerx.entrySet()) {
                    if (player.getKey().calculateScore() == 21) {
                        determineWinner();
                        playAgain = playAgain();

                    }
                    playerTurn(player.getKey(), playerx);
                }
                if(firstTurn) {
                    System.out.println("Dealer's hidden card is: " + dealer.getHand().get(0));
                    firstTurn = false;
                }
                dealerTurn();

                gameOver = true;
                for (Map.Entry<BJPlayer, Integer> playerEntry : playerx.entrySet()) {
                    BJPlayer player = playerEntry.getKey();
                    if(!player.isBusted()) {
                        gameOver = false;
                        break;
                    } else {
                        determineWinner();
                        playAgain = playAgain();

                    }
                }
                if (dealer.isBusted() || dealer.calculateScore() == 21) {
                    gameOver = true;
                    determineWinner();
                    playAgain = playAgain();

                }
            }
            // playAgain = playAgain();
        }

    }

    public void playerTurn(BJPlayer player, HashMap<BJPlayer, Integer> players) {
        // Handle the player's turn (hit or stand)
        System.out.printf("Would %s like to \"hit\", \"stand\", \"double down\", or \"split\"? ", player.getName());
        String choice = keyboard.next();

        switch (choice.toLowerCase()) {
            case "stand" -> System.out.printf("%s is standing.\n", player.getName());
            case "hit" -> {
                BJCard card = deck.dealCard();
                player.addCardToHand(card);
                System.out.printf("%s drew a " + card.toString() + "\n", player.getName());
            }
            case "double_down" -> {
                BJCard newCard = deck.dealCard();
                player.addCardToHand(newCard);
                player.doubleBet();
                System.out.printf("%s drew a " + newCard.toString() + " and increased bet to %d.\n", player.getName(), player.getBet());
            }
            case "split" -> {
                System.out.println(Arrays.toString(players.entrySet().toArray()));
                if (player.getHand().size() != 2) {
                    System.out.println("Cannot Split with less or more than 2 cards in hand.");
                    playerTurn(player, players);
                } else {
                    if (Objects.equals(player.getHand().get(0).getSuit(), player.getHand().get(1).getSuit())) {
                        // TODO: Finish If player split, then create a new deck with that player's same bet
                    } else {
                        System.out.println("Cannot Split since cards are of not same suit.");
                        playerTurn(player, players);
                    }
                }
            }
        }
    }

    public boolean playAgain() {
        System.out.println("Play again? 'yes' or 'no'");
        String answer = keyboard.next();
        return answer.equalsIgnoreCase("yes");
    }

    public void dealerTurn() {
        // Handle the dealer's turn (hit until score >= 17)
        if (dealer.calculateScore() < 17) {
            BJCard card = deck.dealCard();
            dealer.addCardToHand(card);
            System.out.println("Dealer drew a " + card.toString() + "\n");
        } else {
            System.out.println("Dealer is standing.");
        }
    }

    public void determineWinner() {
        BJPlayer winner = null;
        int highestScore = 0;

        for (Map.Entry<BJPlayer, Integer> playerEntry : playerx.entrySet()) {
            BJPlayer player = playerEntry.getKey();

            if(player.calculateScore() <= 21 && player.calculateScore() > highestScore) {
                winner = player;
                highestScore = player.calculateScore();
            }
        }

        if(dealer.calculateScore() <= 21 && dealer.calculateScore() > highestScore) {
            winner = dealer;
        }

        if (winner != null) {
            System.out.println("The winner is: " + winner.getName() + " with a score of " + winner.calculateScore());
        } else {
            System.out.println("No winner. All players busted.");
        }


        // Determine the winner based on the scores
    }
}