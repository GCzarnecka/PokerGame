import java.util.*;

/**
 * Class representing the game.
 */
public class Rozgrywka {

    /**
     * List of players.
     */
    protected  List<Player> players = new ArrayList<>();
    /**
     * Array of clients who are connected
     */
    protected List<ClientHandler> clients;
    /**
     * Assings a player to a client.
     */
    protected Map<Player, ClientHandler> gracz2Client = new HashMap<>();
    /**
     * Initial payment
     */
    private final int ante;
    /**
     * Number of players in the game.
     */
    private int playersCount;
    /**
     * All money that is in the game.
     */
    private int allMoney = 0;
    /**
     * Number of times you will play.
     */
    private final int roundCount;
    public int[] points;

    /**
     * Constructor
     * @param ante - initial payment
     * @param playersCount - number of players in the game
     * @param roundCount - number of rounds
     * @param clients - array of clients who are connected
     */
    public Rozgrywka(int ante, int playersCount, int roundCount, List<ClientHandler> clients) {
        this.ante = ante;
        this.playersCount = playersCount;
        this.roundCount = roundCount;
        this.clients = clients;
    }

    /**
     * It checks if a player has a money to play.
     */
    public void checkMoneyCount() {
        int it = 0;
        Iterator<Player> iter = players.iterator();
        while (iter.hasNext()) {
            Player g = iter.next();
            if (g.money <= 0) {
                g.client.print("You don't have money to play " + it );
                iter.remove();
            }
        }
    }

    /**
     * Deck.init() - initializes Deck of cards and gracz2Client map
     */
    public void prepareGame() {
        Deck.init();
        for (int i = 0; i < playersCount; i++) {
            var g = new Player(clients.get(i));
            gracz2Client.put(g,clients.get(i));
            players.add(g);
        }
    }

    /**
     * It represents the actual game. Each player has to pay ante and then gets 5 cards. Then first bidding takes place, then follows exchange of cards and then
     * a second bidding. It takes place the number of times stored in roundCount. After every round it find the winner and checks if the players have enough money to player longer.
     *
     */
    public void game(){
        int roundNumber = 0;
        prepareGame();

        while (roundNumber < roundCount) {
            for(var client: clients){
                client.print("Round number"+(roundNumber+1));
            }

            allMoney = playersCount * ante;
            for (Player g : players) {
                g.money -= ante;
                g.showCards();
                if (roundNumber != 0) {
                    g.change();
                    g.setPass(false);
                }
                }

            if(bidding()) {
                players.forEach(player -> {
                    player.showCards();
                    player.changeOfCards();
                });
                if(bidding()) winCheck();
            }

            roundNumber++;
        }

        checkMoneyCount();
    }


    /**
     * array- array of players who are still playing
     */
    public void winCheck() {
        var array = new ArrayList<Player>();
        for (Player g : players)
            if (!g.isPass()) array.add(g);
        Player winner = players.get(checkPoints(array,true));
        winner.setMoney(allMoney);
        for (Player g : players) {
            g.client.print("Your money: " + g.getMoney());
            if(g != winner) g.client.print("Player number " + (checkPoints(array,false)+1) +" won the game.");
            else g.client.print("YOU HAVE WON THE GAME! ");
        }
    }

    /**
     * idx - index of player
     * bid - the money we give. You can rise rhe bid if you choose option number 3.
     * Actions: 1.pass 2.check 3.raise bid
     * It returns true or false depending on the number of players that are still in the game. If only one player haven't passed then the game ends and he wins the game.
     */
    public boolean bidding() {
        int bid = 0;
        int idx = 0;
        boolean flag = true;
        do {
            var client = clients.get(idx);
            for(var c:clients) c.print("Player "+(idx+1)+"'s turn " );
            client.print("Choose action:");
            client.print(" 1.pass");
            client.print(" 2.check");
            client.print(" 3.raise bid");
            String choice;

            if (players.get(idx).getMoney() < bid) choice = "1";
            else choice = client.get();
            switch (choice) {
                case "1" -> {flag = case1(idx);}
                case "2" -> {
                    checkMoneyCount();
                    allMoney += bid;
                    players.get(idx ).money -= bid;
                }
                case "3" ->{
                    bid = case3(client, bid, idx);
                    allMoney += bid;
                    players.get(idx).money -= bid;
                }
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
            idx++;

        } while (idx < playersCount && flag);

        return flag;
    }

    /**
     * Case nr.1 from function bidding()
     * @param idx - index of player
     * @return boolean
     */
    public boolean case1(int idx){

            players.get(idx).setPass(true);
            if (playersCheck()) {
                var winnerIndex = index();
                players.get(winnerIndex).setMoney(allMoney);
                for(var g: players)
                {
                    if( players.get(winnerIndex) == g){
                        g.client.print("YOU HAVE WON THE GAME!!!");
                        continue;
                    }
                    g.client.print("End of the game. All money from bid : "+allMoney +" , goes to player number "+ (winnerIndex +1));
                }
                return  false;
            }
            return true;

    }
    /**
     *
     * @param client - client corespondent to a particular player
     * @param bid - bid before a rise
     * @param idx- index of player
     * @return new bid
     */
    public int case3(ClientHandler client, int bid, int idx){
        client.print("Enter new bid: ");
        boolean tmp =true;
        int bidTmp;
        do
        {
            bidTmp = Integer.parseInt(client.get());
            if(players.get(idx).money < bidTmp && bidTmp > bid){
                players.get(idx).client.print("You don't have enough money.");
                continue;
            }
            tmp = false;
        }while(tmp);
        return bidTmp;
    }
    /**
     * Checking if more than one player is in the players array. If only one player has left the game is over.
     * @return true if number of players still in the game if <2
     */
    public boolean playersCheck() {
        int it = 0;
        for (Player g : players) if (!g.isPass()) it++;
        return it < 2;
    }

    /**
     * @return index of player who have won the game.
     */
    public int index() {
        int it = 0;
        for (Player g : players) {
            if (!g.isPass()) {
                return it;
            }
            it++;
        }
        return -1;
    }

    /**
     * It helps to define if the player has a particular set of cards e.g two piars or full.
     * If the player has that set of cards this function will return true.
     * @param func - function as a parameter
     * @param idx - index of player to whom we ascribe points
     * @param it - number of points
     * @return true if func returns true.
     */
    public boolean checkSetOfCards(boolean func, int idx , int it)  {
        if (func) {
            points[idx] = it;
            return false;
        }
        return true;
    }
    /**
     * Function gives every player points depending on how strong their cards are.
     * Player with the most points ( provided other players don't have the same amount of points) wins.
     * If few of the players have the same amount of points, the other parameters are checked.
     * @param array - array of players still in the game
     * @return index of player who have won
     */

    public int checkPoints(List<Player> array, boolean print)  {
        points = new int[array.size()];
        Arrays.fill(points, 0);
        int i = 0;
        for (Player g : array) {
            g.sortCards();
            int it = 9;
            boolean flag = true;
            while (it > 1 && flag) {
                switch (it) {
                    case 9 -> {
                        flag = checkSetOfCards(poker(g), i, it);
                    }
                    case 8 -> {
                        flag = checkSetOfCards(fourOfKind(g), i, it);
                    }
                    case 7 -> {
                        if (full(g) != -1) {
                            points[i] = 7;
                            i++;
                            flag = false;
                        }
                    }
                    case 6 -> {
                        flag = checkSetOfCards(flush(g), i, it);

                    }
                    case 5 -> {
                        flag = checkSetOfCards(straight(g), i, it);
                    }
                    case 4 -> {
                        flag = checkSetOfCards(threeOfKind(g), i, it);
                    }
                    case 3 -> {
                        flag = checkSetOfCards(twoPair(g), i, it);

                    }
                    case 2 -> {
                        flag = checkSetOfCards(onePair(g), i, it);

                    }
                    case 1 -> {
                        points[i] = 1;
                        if (print) g.client.print("You had : No Pair");
                        i++;
                        flag = false;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + it);
                }
                it--;

            }
            i++;
        }

        return chooseWinner(array);
    }

    /**
     * Checks which player have the most valuable cards.
     * @param array - players still in the game
     * @return the winner
     */
    public int chooseWinner(List<Player> array){

        int max = Arrays.stream(points).max().getAsInt();
        int winner = -1;
        for (int it = 0; it < points.length; it++)
            if (points[it] == max) {
                winner = it;
                break;
            }
        for (int it = 0; it < points.length; it++) {

            if (points[it] == max && it != winner) {
                if (points[it] == 7) {
                    max = points[it];
                    winner = it;
                }
                if (array.get(it).playersCards.get(4).getRank().value > array.get(winner).playersCards.get(4).getRank().value) {
                    max = points[it];
                    winner = it;
                }
            }
        }
        return winner;
    }

    /**
     * Checking which player has more valuable card in three of a kind
     * @param idx1 - 1's player index
     * @param idx2 - 2's player index
     * @return idndex of a player who has more powerful cards
     */
    public int maxthreeOfKind(int idx1, int idx2) {
        if (full(players.get(idx1)) > full(players.get(idx2))) return idx1;
        return idx2;
    }

    /**
     * Straight flush (poker)
     *
     * @param g: object of a Player class
     * @return true if a hand contains five cards of sequential rank, all of the same suit
     */
    public static boolean poker(Player g) {
        return flush(g) && straight(g);
    }

    /**
     * Quads/Four of a kind (kareta)
     *
     * @param g: object of a Player class
     * @return true if a hand contains four cards of one rank and one card of another rank
     */
    public static boolean fourOfKind(Player g) {
        int similarities = 1;
        for (int i = 0; i < 4; i++) {
            if (g.getPlayersCards().get(i).getRank().value == g.getPlayersCards().get(i + 1).getRank().value)
                similarities++;
            else {
                similarities = 1;
            }
        }
        return similarities == 4;
    }

    /**
     * Full house (lub po prostu full) - two pair and three of a kind at the same time
     * <p>
     * @param g: object of a Player class
     * @return true if a hand contains three cards of one rank and two cards of another rank
     */
    public static int full(Player g) {
        int similarities = 1;
        int value = -1;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (g.getPlayersCards().get(i).getRank().value == g.getPlayersCards().get(j).getRank().value) {
                    similarities++;
                }
            }
            if (similarities == 3) {
                value = g.getPlayersCards().get(i).getRank().value;
                break;
            }
            similarities = 1;
        }
        var pair = onePair(g);
        return pair? value: -1;
    }

    /**
     * Flush (kolor)
     *
     * @param g: object of a Player class
     * @return true if a hand contains five cards - all of the same suit, not all of sequential rank
     */
    public static boolean flush(Player g) {
        for (int i = 0; i < 4; i++) {
            if (!g.getPlayersCards().get(i).getSuit().equals(g.getPlayersCards().get(i + 1).getSuit())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Straight
     *
     * @param g: object of a Player class
     * @return true if hand contains five cards of sequential rank, not all of the same suit
     */
    public static boolean straight(Player g) {
        g.sortCards();
        for (int i = 0; i < 4; i++) {
            if(g.getPlayersCards().get(i).getRank().value != g.getPlayersCards().get(i+1).getRank().value -1) return false;
        }
        return true;
    }

    /**
     * Three of a kind
     *
     * @param g: object of a Player class
     * @return true if there are three card of a kind
     */
    public static boolean threeOfKind(Player g) {
        int similarities = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (g.getPlayersCards().get(i).getRank().value == g.getPlayersCards().get(j).getRank().value) {
                    similarities++;
                }
            }
            if (similarities == 3)  return true;
            else {
                similarities = 1;
            }
        }
        return false;
    }

    /**
     * Two pair
     * @param g: object of a Player class
     * @return true if there are two pair
     */
    public static boolean twoPair(Player g) {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (g.getPlayersCards().get(i).getRank().value == g.getPlayersCards().get(j).getRank().value) {
                    counter++;
                }
            }
        }
        return counter == 2;
    }

    /**
     * One pair
     * @param g: object of a Player class
     * @return true if there is one pair
     */
    public static boolean onePair(Player g) {
        int similarities = 1;
        var pair = false;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (g.getPlayersCards().get(i).getRank().value == g.getPlayersCards().get(j).getRank().value) {
                    similarities++;
                }
            }
            if (similarities == 2) {
                pair = true;
                break;
            }
            similarities = 1;
        }
        return pair;
    }

}

