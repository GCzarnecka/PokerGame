import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * Class representing a deck of cards
 * cards - array containing 52 objects of class Card
 * numberOfCards - number of cards left
 */
public class Deck {
    public static List<Card> cards = new ArrayList<Card>(52);
    public static List<Card> thrownCards = new ArrayList<Card>(52);
    public static int numberOfCards = 52;
    private static Random rand;

    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initiallizes new Deck of cards
     */
    public static void init(){
        numberOfCards = 52;
        thrownCards = new ArrayList<>(52);
        cards = new ArrayList<>(52);
        Card.Suit[] suit =  Card.Suit.values();
        for(Card.Suit s:suit){
            Card.Rank[] tab = Card.Rank.values();
            for(Card.Rank r:tab){
                Card card = new Card(r,s);
                cards.add(card);
            }
        }
    }

    /**
     * Constructor
     */
    private Deck(){}

    /**
     * Prints rank and suit for each card in array cards.
     */
    public static void printCards(){
        for(Card c:cards) System.out.println(c.getRank()+" "+c.getSuit());
    }

    /**
     * It deals one card to the player.
     */
    public static Card pullCard(){
        if(numberOfCards == 0){
            printCards();
            cards.addAll(thrownCards);
            shuffle();
            printCards();

        }
        int index = rand.nextInt(numberOfCards);
        Card c = new Card(cards.get(index));
        cards.remove(cards.get(index));
        numberOfCards--;
        return c;
    }

    /**
     * It exchange places of two cards.
     * @param i -  index of first Card object
     * @param j -  index of second Card object
     */
    static void swap(int i, int j){
        Card c = new Card(cards.get(i));
        cards.set(i, cards.get(j));
        cards.set(j, c);
    }


    /**
     * Shuffling the deck of cards
     * It swaps two randomly choosen cards.
     */
    public static void shuffle(){
        for(int i = 0;i<15;i++){
            int n1 = rand.nextInt(numberOfCards);
            int n2 = rand.nextInt(numberOfCards);
            swap(n1,n2);
        }
    }

}
