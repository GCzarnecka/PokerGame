import java.util.ArrayList;

/**
 * Class representing a player
 */
public class Player {
    /**
     * cards which player gets
     */
    public  ArrayList<Card> playersCards = new ArrayList<Card>(5);
    /**
     *  matches player with a client
     */
    public ClientHandler client;
    /**
     * false if player haven't passed
     */
    public boolean pass = false;
    /**
     *  money that you have
     */
    public int money = 100;

    public ArrayList<Card> getPlayersCards() {
        return playersCards;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money += money;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    /**
     * Constructor
     * @param client that corresponds a particular player
     */
    Player(ClientHandler client){
        for(int i = 0;i < 5;i++) playersCards.add(Deck.pullCard());
        this.client = client;
    }

    /**
     * Default constructor
     */
    Player(){}


    /**
     * Prints Cards for the particular player.
     */
    void showCards(){
        for(Card c:playersCards) client.print(c.getSuit() + " " +c.getRank());
    }

    /**
     * Removes card at given index
     */
    public void throwCard(int index){
        Deck.thrownCards.add(this.playersCards.get(index));
        playersCards.remove(index);
    }

    /**
     * It uses throwCard to remove all cards from playersCards
     * and pullCard to pull 5 new cards from Deck.
     */
    public void change(){
        var tmp = new ArrayList<Card>();
        for(int i = 4; i>=0; i--){
            throwCard(i);
            tmp.add(Deck.pullCard());
        }
        playersCards.addAll(tmp);
    }

    /**
     *  Asks client if he wants to change any cards and if he does, it asks him subsequently which cards.
     */
    public void changeOfCards(){
        client.print("Do you want to change your cards? y/n:");
        String in = client.get();
        if (in.equals("n")){
            client.print("You have not changed any card");
            return;
        }
        client.print("Which cards do you want to change? ");
        int number = 0;
        for(int i =0; i<5; i++){
            client.print(playersCards.get(i).getSuit() + " " + playersCards.get(i).getRank());
            client.print("Change? : y/n:");
            String s = client.get();
            if(s.equals("y") || s.equals("Y")){
                throwCard(0);
                Card c = Deck.pullCard();
                playersCards.add(c);
                number++;
            }
        }
        client.print("You have changed  " +number+ " cards");
        client.print("Your cards now : ");
        showCards();
    }

    /**
     * Interchange two cards.
     * @param i - first card index
     * @param j - second card index
     */
    public void swap(int i, int j){
        Card c = new Card(playersCards.get(i));
        playersCards.set(i, playersCards.get(j));
        playersCards.set(j, c);
    }

    /**
     *  Sorts player's cards by rank and suit
     */
    public  void sortCards(){
        for(int i=0;i<5;i++){
            for(int j = i + 1;j < 5; j++){
                if(playersCards.get(i).getRank().value > playersCards.get(j).getRank().value){
                    swap(i,j);
                }
                if(playersCards.get(i).getRank().value >= playersCards.get(j).getRank().value){
                        swap(i,j);
                }
            }
        }
    }
}
