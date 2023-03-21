import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {


    @Test
    void pullCard() {
        Deck.init();
        var temp = new HashSet<Card>();
        for(int i=0; i<52; i++)
            temp.add(Deck.pullCard());
        assertEquals(52,temp.size());
    }

    @Test
    void shuffle() {
        Deck.init();
        var temp = new ArrayList<Card>(Deck.cards);
        Deck.shuffle();
        boolean flag = true;
        for(int i=0;i<52;i++)
        {
            if (Deck.cards.get(i) != temp.get(i)) {
                flag = false;
                break;
            }
        }
        assertFalse(flag);
    }
}