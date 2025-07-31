package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TradingCardGame {
    public void startGame() {
        var gameEnded = false;

        Player playerOne = new Player(new Health(30), new Mana(0));
        Player playerTwo = new Player(new Health(30), new Mana(0));

        var playerOneDeck = getStartingDeck();
        var playerOneCurrentHand = getInitialHand(playerOneDeck);
        var playerTwoDeck = getStartingDeck();
        var playerTwoCurrentHand = getInitialHand(playerTwoDeck);

        while(!gameEnded) {

            System.out.println("\nPlayer 1 \nHealth:" + playerOne.health() + "\nMana" + playerOne.mana());

            System.out.println("\n******Player 1 Deck******\n" + playerOneDeck);
            playerOneDeck.forEach(card -> System.out.println("Card Mana: " + card.ManaCost()));
            System.out.println("\n******Player 1 Hand******\n" + playerOneDeck);
            playerOneCurrentHand.forEach(card -> System.out.println("Card Mana: " + card.ManaCost()));

            System.out.println("\nPlayer 2 \nHealth:" + playerTwo.health() + "\nMana" + playerTwo.mana());
            System.out.println("\n******Player 2 Deck******\n");
            playerTwoDeck.forEach(card -> System.out.println("Card Mana: " + card.ManaCost()));
            System.out.println("\n******Player 2 Hand******\n" + playerTwoDeck);
            playerTwoCurrentHand.forEach(card -> System.out.println("Card Mana: " + card.ManaCost()));

        }
    }

    private List<Card> getStartingDeck() {
        ArrayList<Card> deck = new ArrayList<>();

        deck.add(new Card(0));
        deck.add(new Card(0));
        deck.add(new Card(1));
        deck.add(new Card(1));
        deck.add(new Card(2));
        deck.add(new Card(2));
        deck.add(new Card(2));
        deck.add(new Card(3));
        deck.add(new Card(3));
        deck.add(new Card(3));
        deck.add(new Card(3));
        deck.add(new Card(4));
        deck.add(new Card(4));
        deck.add(new Card(4));
        deck.add(new Card(5));
        deck.add(new Card(5));
        deck.add(new Card(6));
        deck.add(new Card(6));
        deck.add(new Card(7));
        deck.add(new Card(8));

        return deck;
    }

    private List<Card> getInitialHand(List<Card> deck) {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(pickCard(deck, Optional.empty()));
        hand.add(pickCard(deck, Optional.empty()));
        hand.add(pickCard(deck, Optional.empty()));
        return hand;
    }

    private Card pickCard(List<Card> deck, Optional<Integer> previousPosition) {

        Random rand = new Random();
        var pick =  rand.nextInt(deck.size() - 1);

        if (previousPosition.isPresent()) {
            if (previousPosition.get() == pick) {
                return pickCard(deck, Optional.of(pick));
            } else {
                return deck.get(pick);
            }
        } else {
            return deck.get(pick);
        }
    }
}

record Player(Health health, Mana mana) {}

record Health(int value){}
record Mana(int value){}

record Card(int ManaCost){ }
