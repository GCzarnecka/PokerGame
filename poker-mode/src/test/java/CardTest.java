import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testEquals() {
        var card1 = new Card(Card.Rank.AS, Card.Suit.PIK);
        var card2 = new Card(Card.Rank.AS, Card.Suit.PIK);
        assertEquals(card1, card2);
    }

}