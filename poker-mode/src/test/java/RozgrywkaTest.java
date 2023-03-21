import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RozgrywkaTest {

    @org.junit.jupiter.api.Test
    void checkMoneyCount() {
        var list = new ArrayList<ClientHandler>();
        list.add(new ClientHandler(new ByteArrayInputStream("".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,1,2,list);
        rozgrywka.prepareGame();
        rozgrywka.players.get(0).money=-1;
        rozgrywka.checkMoneyCount();
        assertEquals(0, rozgrywka.players.size());
    }

    @org.junit.jupiter.api.Test
    void prepareGame() {
        var list = new ArrayList<ClientHandler>();
        var playersCount = 10;
        for(int i=0; i<playersCount; i++)
            list.add(new ClientHandler(new ByteArrayInputStream("".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,playersCount,2,list);
        rozgrywka.prepareGame();
        assertEquals(rozgrywka.players.size(), playersCount);
    }

    @org.junit.jupiter.api.Test
    void gra() throws Exception {
        var list = new ArrayList<ClientHandler>();
        list.add(new ClientHandler(new ByteArrayInputStream("2\n".getBytes())));
        list.add(new ClientHandler(new ByteArrayInputStream("1\n".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,2,1,list);
        rozgrywka.game();
        assertEquals(110, rozgrywka.players.get(0).money);
    }

    @org.junit.jupiter.api.Test
    void winCheck() throws Exception {
        var list = new ArrayList<ClientHandler>();
        var playersCount = 2;
        for(int i=0; i<playersCount; i++)
            list.add(new ClientHandler(new ByteArrayInputStream("".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,playersCount,2,list);
        rozgrywka.prepareGame();
        rozgrywka.players.get(0).pass=true;
        rozgrywka.winCheck();
        assertEquals(100, rozgrywka.players.get(1).money);
    }

    @org.junit.jupiter.api.Test
    void licytacja() {
        var list = new ArrayList<ClientHandler>();
        list.add(new ClientHandler(new ByteArrayInputStream("3\n50".getBytes())));
        list.add(new ClientHandler(new ByteArrayInputStream("2\n".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,2,1,list);
        rozgrywka.prepareGame();
        rozgrywka.bidding();
        assertEquals(50, rozgrywka.players.get(0).money);
        assertEquals(50, rozgrywka.players.get(1).money);
    }

    @org.junit.jupiter.api.Test
    void czyKoniec() {
        var list = new ArrayList<ClientHandler>();
        var playersCount = 2;
        for(int i=0; i<playersCount; i++)
            list.add(new ClientHandler(new ByteArrayInputStream("".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,playersCount,2,list);
        rozgrywka.prepareGame();
        rozgrywka.players.get(0).pass=true;
        assertEquals(true, rozgrywka.playersCheck());
    }

    @org.junit.jupiter.api.Test
    void index() {
        var list = new ArrayList<ClientHandler>();
        var playersCount = 2;
        for(int i=0; i<playersCount; i++)
            list.add(new ClientHandler(new ByteArrayInputStream("".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,playersCount,2,list);
        rozgrywka.prepareGame();
        rozgrywka.players.get(0).pass=true;
        assertEquals(1, rozgrywka.index());
    }

    @org.junit.jupiter.api.Test
    void sprawdz() throws Exception {
        var gracze = new ArrayList<Player>();
        var playersCount = 2;
        for(int i=0; i<playersCount; i++)
            gracze.add(new Player());
        var list = new ArrayList<ClientHandler>();
        for(int i=0; i<playersCount; i++)
            list.add(new ClientHandler(new ByteArrayInputStream("".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,playersCount,2,list);

        gracze.get(0).playersCards.add(0,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        gracze.get(0).playersCards.add(1,new Card(Card.Rank.FIVE, Card.Suit.KARO));
        gracze.get(0).playersCards.add(2,new Card(Card.Rank.SEVEN, Card.Suit.KARO));
        gracze.get(0).playersCards.add(3,new Card(Card.Rank.EIGHT, Card.Suit.KARO));
        gracze.get(0).playersCards.add(4,new Card(Card.Rank.AS, Card.Suit.KARO));

        gracze.get(1).playersCards.add(0,new Card(Card.Rank.FOUR, Card.Suit.KIER));
        gracze.get(1).playersCards.add(1,new Card(Card.Rank.FIVE, Card.Suit.KIER));
        gracze.get(1).playersCards.add(2,new Card(Card.Rank.SEVEN, Card.Suit.KIER));
        gracze.get(1).playersCards.add(3,new Card(Card.Rank.EIGHT, Card.Suit.KIER));
        gracze.get(1).playersCards.add(4,new Card(Card.Rank.AS, Card.Suit.PIK));

        assertEquals(0, rozgrywka.checkPoints(gracze,false));

    }

    @org.junit.jupiter.api.Test
    void maxTrojka() {
        var gracze = new ArrayList<Player>();
        var playersCount = 2;
        for(int i=0; i<playersCount; i++)
            gracze.add(new Player());
        var list = new ArrayList<ClientHandler>();
        for(int i=0; i<playersCount; i++)
            list.add(new ClientHandler(new ByteArrayInputStream("".getBytes())));
        Rozgrywka rozgrywka = new Rozgrywka(10,playersCount,2,list);

        gracze.get(0).playersCards.add(0,new Card(Card.Rank.FOUR, Card.Suit.PIK));
        gracze.get(0).playersCards.add(1,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        gracze.get(0).playersCards.add(2,new Card(Card.Rank.AS, Card.Suit.KARO));
        gracze.get(0).playersCards.add(3,new Card(Card.Rank.AS, Card.Suit.PIK));
        gracze.get(0).playersCards.add(4,new Card(Card.Rank.AS, Card.Suit.KIER));

        gracze.get(1).playersCards.add(0,new Card(Card.Rank.FIVE, Card.Suit.PIK));
        gracze.get(1).playersCards.add(1,new Card(Card.Rank.FIVE, Card.Suit.KIER));
        gracze.get(1).playersCards.add(2,new Card(Card.Rank.EIGHT, Card.Suit.KIER));
        gracze.get(1).playersCards.add(3,new Card(Card.Rank.EIGHT, Card.Suit.KARO));
        gracze.get(1).playersCards.add(4,new Card(Card.Rank.EIGHT, Card.Suit.PIK));
        rozgrywka.players =gracze;
        assertEquals(0, rozgrywka.maxthreeOfKind(0,1));


    }

    @org.junit.jupiter.api.Test
    void poker() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.TEN, Card.Suit.KIER));
        g.playersCards.add(1,new Card(Card.Rank.J, Card.Suit.KIER));
        g.playersCards.add(2,new Card(Card.Rank.QUEEN, Card.Suit.KIER));
        g.playersCards.add(3,new Card(Card.Rank.KING, Card.Suit.KIER));
        g.playersCards.add(4,new Card(Card.Rank.AS, Card.Suit.KIER));
        assertTrue(Rozgrywka.poker(g));
    }

    @org.junit.jupiter.api.Test
    void quads() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.FOUR, Card.Suit.PIK));
        g.playersCards.add(1,new Card(Card.Rank.AS, Card.Suit.TREFL));
        g.playersCards.add(2,new Card(Card.Rank.AS, Card.Suit.KARO));
        g.playersCards.add(3,new Card(Card.Rank.AS, Card.Suit.PIK));
        g.playersCards.add(4,new Card(Card.Rank.AS, Card.Suit.KIER));
        assertTrue(Rozgrywka.fourOfKind(g));
    }

    @org.junit.jupiter.api.Test
    void full() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.FOUR, Card.Suit.PIK));
        g.playersCards.add(1,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        g.playersCards.add(2,new Card(Card.Rank.AS, Card.Suit.KARO));
        g.playersCards.add(3,new Card(Card.Rank.AS, Card.Suit.PIK));
        g.playersCards.add(4,new Card(Card.Rank.AS, Card.Suit.KIER));
        assertNotEquals(-1,Rozgrywka.full(g));

    }

    @org.junit.jupiter.api.Test
    void flush() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.FIVE, Card.Suit.KARO));
        g.playersCards.add(1,new Card(Card.Rank.TWO, Card.Suit.KARO));
        g.playersCards.add(2,new Card(Card.Rank.TEN, Card.Suit.KARO));
        g.playersCards.add(3,new Card(Card.Rank.AS, Card.Suit.KARO));
        g.playersCards.add(4,new Card(Card.Rank.KING, Card.Suit.KARO));
        assertTrue(Rozgrywka.flush(g));
    }

    @org.junit.jupiter.api.Test
    void straight() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.THREE, Card.Suit.KARO));
        g.playersCards.add(1,new Card(Card.Rank.TWO, Card.Suit.KARO));
        g.playersCards.add(2,new Card(Card.Rank.FIVE, Card.Suit.KARO));
        g.playersCards.add(3,new Card(Card.Rank.SIX, Card.Suit.KARO));
        g.playersCards.add(4,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        assertTrue(Rozgrywka.straight(g));
    }

    @org.junit.jupiter.api.Test
    void threeOfKind() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.THREE, Card.Suit.KARO));
        g.playersCards.add(1,new Card(Card.Rank.THREE, Card.Suit.KIER));
        g.playersCards.add(2,new Card(Card.Rank.THREE, Card.Suit.PIK));
        g.playersCards.add(3,new Card(Card.Rank.SIX, Card.Suit.KARO));
        g.playersCards.add(4,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        assertTrue(Rozgrywka.threeOfKind(g));
    }

    @org.junit.jupiter.api.Test
    void twoPair() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.THREE, Card.Suit.KARO));
        g.playersCards.add(1,new Card(Card.Rank.THREE, Card.Suit.KIER));
        g.playersCards.add(2,new Card(Card.Rank.FOUR, Card.Suit.PIK));
        g.playersCards.add(3,new Card(Card.Rank.SIX, Card.Suit.KARO));
        g.playersCards.add(4,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        assertTrue(Rozgrywka.twoPair(g));
    }

    @org.junit.jupiter.api.Test
    void onePair() {
        Player g =new Player();
        g.playersCards.add(0,new Card(Card.Rank.THREE, Card.Suit.KARO));
        g.playersCards.add(1,new Card(Card.Rank.THREE, Card.Suit.KIER));
        g.playersCards.add(2,new Card(Card.Rank.AS, Card.Suit.PIK));
        g.playersCards.add(3,new Card(Card.Rank.SIX, Card.Suit.KARO));
        g.playersCards.add(4,new Card(Card.Rank.FOUR, Card.Suit.KARO));
        assertTrue(Rozgrywka.onePair(g));
    }
}