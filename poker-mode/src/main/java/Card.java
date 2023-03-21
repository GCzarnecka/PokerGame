import java.util.Objects;

/**
 * Class representing a single card.
 */
public class Card {
    /**
     * Enum class representing rank of a card
     */
    enum Rank {
        AS(14), KING(13), QUEEN(12), J(11), TEN(10), NINE(9), EIGHT(8),SEVEN(7),SIX(6),FIVE(5), FOUR(4), THREE(3), TWO(2);
        int value;
        Rank(int i) {
            value =i;
        }
    }
    /**
     * Enum class representing suit of a card
     */
    enum Suit{
        PIK(4), KIER(3), KARO(2), TREFL(1);
        int value;
        Suit(int i) {
            value =i;
        }
    }

    /**
     * Object of class Rank
     */
    private Rank rank;
    /**
     * Object of class Suit
     */
    private Suit suit;

    /**
     * getter
     * @return enum
     */
    public Rank getRank() {
        return rank;
    }
    /**
     * getter
     * @return enum
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Constructor with parameters
     * @param r obj of class Rank
     * @param s obj of class Suit
     */
    Card(Rank r, Suit s){
        this.rank = r;
        this.suit = s;
    }

    /**
     * Copy constructor
     * @param c - object Card
     */
    public Card(Card c){
        rank = c.rank;
        suit = c.suit;
    }

    /**
     * Constructor
     */
    Card(){}

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

}
