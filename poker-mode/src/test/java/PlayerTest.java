import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {


    @Test
    void throwCard() {
        Deck.init();
        Player g =new Player();
        Card c = new Card(Card.Rank.THREE, Card.Suit.KARO);
        g.playersCards.add(0,c);
        g.playersCards.add(1,new Card(Card.Rank.THREE, Card.Suit.KIER));
        g.playersCards.add(2,new Card(Card.Rank.FOUR, Card.Suit.PIK));
        g.playersCards.add(3,new Card(Card.Rank.SIX, Card.Suit.KARO));
        g.playersCards.add(4,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        g.throwCard(0);
        assertEquals(4, g.playersCards.size());
        assertTrue(Deck.thrownCards.contains(c));
    }

    @Test
    void wymien() {
        Deck.init();
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.THREE, Card.Suit.KARO));
        g.playersCards.add(1,new Card(Card.Rank.THREE, Card.Suit.KIER));
        g.playersCards.add(2,new Card(Card.Rank.FOUR, Card.Suit.PIK));
        g.playersCards.add(3,new Card(Card.Rank.SIX, Card.Suit.KARO));
        g.playersCards.add(4,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        var temp = new ArrayList<Card>(g.playersCards);
        boolean flag = true;
        g.change();
        for(int i=0;i<5;i++){
            if (g.playersCards.get(i) != temp.get(i)) {
                flag = false;
                break;
            }
        }
        assertFalse(flag);
    }

    @Test
    void changeOfCards() {
        Deck.init();
        var g = new Player(new ClientHandler(new ByteArrayInputStream("y\ny\ny\nn\nn\nn".getBytes())));
        var temp = new ArrayList<Card>(g.playersCards);
        g.changeOfCards();
        int counter = 0;

        for(var c : temp) {
            for(var x : g.playersCards) {
                if(c.getRank().value==x.getRank().value && c.getSuit().value==x.getSuit().value) counter++;
            }
        }
        assertEquals(3,counter);
    }

}