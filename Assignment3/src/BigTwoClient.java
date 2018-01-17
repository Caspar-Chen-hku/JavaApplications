import java.util.ArrayList;
import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 * <h1>this is the BigTwoClient class!</h1>
 * The BigTwoClient class implements the CardGame interface 
 * and NetworkGame interface. It is used to model a Big Two 
 * card game that supports 4 players playing over the Internet.
 * <p> 
 * @author CChen
 * @since 2016-11-23
 * @version 1.0
 */
public class BigTwoClient implements CardGame,NetworkGame{
	
	/**
	 * a constructor for creating a Big Two client.
	 * (i) create 4 players and add them to the list of players; 
	 * (ii) create a Big Two table which builds the GUI for the game 
	 * and handles user actions; and 
	 * (iii) make a connection to the game server by calling the 
	 * makeConnection() method from the NetworkGame interface. 
	 */
	public BigTwoClient(){
		playerList.add(new CardGamePlayer());
    	playerList.add(new CardGamePlayer());
    	playerList.add(new CardGamePlayer());
    	playerList.add(new CardGamePlayer());
    	table = new BigTwoTable(this);
    	makeConnection();
	}
    public static int numOfPlayers=4;
    private Deck deck;
    public static ArrayList<CardGamePlayer> playerList =new ArrayList<CardGamePlayer>();;
    public static ArrayList<Hand>	handsOnTable= new ArrayList<Hand>();
    private int	playerID;
    private String playerName;
    private String	serverIP="127.0.0.1";
    private int	serverPort=2396;
    private Socket	sock;
    private ObjectOutputStream	oos;
    public static int	currentIdx;
    private BigTwoTable	table;
    public static int last;
    public int lth;
    public static Hand lasthand;
    
    /**
     * ¨C a method for getting the number of players. 
     */
    public int getNumOfPlayers(){
    	return BigTwoClient.numOfPlayers;
    }
    
    /**
     * a method for getting the deck of cards being used. 
     */
    public Deck getDeck(){
    	return deck;
    }
    
    /**
     * a method for getting the list of players
     */
    public ArrayList<CardGamePlayer> getPlayerList(){
    	return BigTwoClient.playerList;
    }
    
    /**
     * a method for getting the list of hands played on the table. 
     */
    public ArrayList<Hand>	getHandsOnTable(){
    	return BigTwoClient.handsOnTable;
    }
    
    /**
     *  a method for getting the index of the player for the current turn. 
     */
    public int	getCurrentIdx(){
    	return currentIdx;
    }
    
    /**
     * method to start the game
     * @param deck the deck of cards
     */
    public void start(Deck deck){
    	for (int i=0; i<4; i++){
    		BigTwoClient.playerList.get(i).removeAllCards();
    	}
    	BigTwoClient.handsOnTable= new ArrayList<Hand>();
    	table.reset();//reset the table
       	for (int i=0; i<49; i+=4){
       		BigTwoClient.playerList.get(0).addCard(deck.getCard(i));		
       	}
       	playerList.get(0).sortCardsInHand();
       	for (int i=1; i<50; i+=4){
       		BigTwoClient.playerList.get(1).addCard(deck.getCard(i));
       	}
       	BigTwoClient.playerList.get(1).sortCardsInHand();
       	for (int i=2; i<51; i+=4){
       		BigTwoClient.playerList.get(2).addCard(deck.getCard(i));
       	}
       	BigTwoClient.playerList.get(2).sortCardsInHand();
       	for  (int i=3; i<52; i+=4){
       		BigTwoClient.playerList.get(3).addCard(deck.getCard(i));
       	}
       	BigTwoClient.playerList.get(3).sortCardsInHand();//deliver the cards
       	table.setActivePlayer(firstPlayer());
       	BigTwoClient.currentIdx=firstPlayer();
       table.printMsg(currentIdx+"");
       	table.repaint();
    }
    
    private int firstPlayer(){
    	Card test = new Card(0,2);
    	for (int i=0; i<4; i++){
    			if (BigTwoClient.playerList.get(i).getCardsInHand().contains(test))
    			{ 
        			return i;}
    	}
    	return -1;
    }
    
    /**
     * a method for making a move by a player with the specified playerID 
     * using the cards specified by the list of indices. This method should 
     * be called from the BigTwoTable when the local player presses either 
     * the ¡°Play¡± or ¡°Pass¡± button. create a CardGameMessage object of the 
     * type MOVE,	with the playerID and data in this message being -1 and 
     * cardIdx, respectively, and send it to the game server using the 
     * sendMessage() method from the NetworkGame interface. 
     */
    public void	makeMove(int playerID,	int[] cardIdx){
    	CardGameMessage msg = new CardGameMessage(6,playerID,cardIdx);
    	sendMessage(msg);
    	table.repaint();
    }
    
    /**
     * a method for checking a move made by a player. This method should be 
     * called from the parseMessage() method from the NetworkGame interface 
     * when a message of the type MOVE is received from the game server. 
     * The playerID and data in this message give the playerID of the player 
     * who makes the move and a reference to a regular array of integers 
     * specifying the indices of the selected cards, respectively. These are 
     * used as the arguments in calling the checkMove() method.
     */
    public void checkMove(int playerID, int[] cardIdx){
    	if (endOfGame()) return;
    	if (BigTwoClient.lasthand==null){
    		if (cardIdx==null){
    			table.printMsg("Not a legal move!!!");
    			table.legal=false;
    			table.resetSelected();
            	return;
    		}
    		Card test = new Card(0,2);
    		table.a = composeHand(BigTwoClient.playerList.get(playerID),BigTwoClient.playerList.get(playerID).play(cardIdx));
    		lth = cardIdx.length;
    		if (table.a==null){
    			table.legal=false;
    			table.printMsg("Not a legal move!!!");
    			table.resetSelected();
    			return;
    		}
    		if (table.a!=null&&table.a.contains(test)) {
    			table.legal=true;
    			return;
    		}
    		table.printMsg("Not a legal move!!!");
    		table.legal=false;
    		table.resetSelected();
    	}
    	else if (BigTwoClient.lasthand!=null&&cardIdx==null&&BigTwoClient.last!=BigTwoClient.currentIdx) {
    		table.legal=true;
    		table.a=null;
    		return;
    	}
    	else if (BigTwoClient.lasthand!=null&&cardIdx==null&&BigTwoClient.last==BigTwoClient.currentIdx){
        	table.printMsg("Not a legal move!!!");
        	table.resetSelected();
        	table.legal=false;
    		return;
        }
    	else if (BigTwoClient.lasthand!=null&&cardIdx!=null){
		table.a = composeHand(BigTwoClient.playerList.get(BigTwoClient.currentIdx),BigTwoClient.playerList.get(BigTwoClient.currentIdx).play(cardIdx));
		if (table.a==null) {
			table.legal=false;
			table.printMsg("Not a legal move!!!");
			table.resetSelected();
			return;
		}
		if (!table.a.isEmpty()&&(BigTwoClient.last==BigTwoClient.currentIdx||table.a.beats(BigTwoClient.lasthand))) {
			lth=cardIdx.length;
			table.legal=true;
			return;
		}
		table.printMsg("Not a legal move!!!");
		table.legal=false;
		table.resetSelected();
		}
    }
    
    /**
     * a method for checking if the game ends
     */
    public boolean endOfGame(){
    	if (empty()>=0) return true;
    	return false;
    }
    
    private int empty(){
    	int emp=-1;
		for (int i=0; i<4; i++){
			if (BigTwoClient.playerList.get(i).getCardsInHand().isEmpty()){
				emp=i;
			break;}
		}
		if (emp==-1) return -1;
		for (int i=emp+1; emp<4; emp++){
			if (BigTwoClient.playerList.get(i).getCardsInHand().isEmpty())
				return -1;
		}
		return emp;
	}
    
    /**
     * a method for returning a valid hand from the specified list 
     * of cards of the player. Returns null is no valid hand can be 
     * composed from the specified list of cards.
     * @param player CardGamePlayer, the player who chooses this hand
     * @param cards CardList, the cards that he chooses
     * @return a Hand or null
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards){
		Hand a=null;
		if (cards==null) return null;
		a = new Single(player,cards);
		if (a.isValid()) return a;
		a = new Pair(player,cards);
		if (a.isValid()) return a;
		a = new Triple(player,cards);
		if (a.isValid()) return a;
		a = new StraightFlush(player,cards);
		if (a.isValid()) return a;
		a = new Flush(player,cards);
		if (a.isValid()) {return a;}
		a = new FullHouse(player,cards);
		if (a.isValid()) return a;
		a = new Quad(player,cards);
		if (a.isValid()) return a;
		a = new Straight(player,cards);
		if (a.isValid()) return a;
		return null;
	}
    
    /**
     *  a method for getting the playerID (i.e., index) of the local player. 
     */
    public int	getPlayerID(){
    	return playerID;
    }
    
    /**
     *  a method for setting the playerID (i.e., index) of the local player. 
     *  This method should be called from the parseMessage() method when a 
     *  message of the type PLAYER_LIST is received from the game server. 
     */
    public void  setPlayerID(int	playerID){
    	this.playerID=playerID;
    }
    
    /**
     * a method for setting serverPort 
     */
   public void setServerPort(int port){
	   serverPort=port;
   }
   
   /**
    *  a method for getting the name of the local player. 
    */
    public String	getPlayerName(){
    	return playerName;
    }
    
    /**
     * a method for setting the name of the local player.  
     */
    public void	setPlayerName(String	playerName){
    	this.playerName=playerName;
    }
    
    /**
     * a method for getting the IP address of the game server. 
     */
    public String	getServerIP(){
    	return serverIP;
    }
    
    /**
     * a method for setting the IP address of the game server.
     */
    public void	setServerIP(String	serverIP){
    	this.serverIP= serverIP;  }
    
    /**
     * a method for getting the TCP port of the game server. 
     */
    public int	getServerPort(){
    	return serverPort;
    }
    
    /**
     * a method for setting the TCP port of the game server. 
     * @param serverPort int 
     */
    public void	setServerIP(int	serverPort){
    	this.serverPort=serverPort;
    }
    
    /**
     * a method for making a socket connection with the game server. 
     * Upon successful connection,(i) create an ObjectOutputStream for 
     * sending messages to the game server; (ii) create a thread for 
     * receiving messages from the game server; (iii) send a message 
     * of the type JOIN to the game server, with playerID being -1	
     * and data being a reference to a string representing the name 
     * of the local player; (iv) send a message of the type READY to 
     * the game server, with playerID and data being -1 and null, respectively.
     */
    public synchronized void makeConnection(){
    	try {
    		sock = new Socket(serverIP, serverPort);
    		oos = new ObjectOutputStream(sock.getOutputStream());
    		ServerHandler threadJob = new ServerHandler();
    		Thread myThread = new Thread(threadJob);
    		myThread.start();
    		//this.start(deck);
    		CardGameMessage msg = new CardGameMessage(1,-1,playerName);
        	sendMessage(msg);
        	msg = new CardGameMessage(4,-1,null);
        	sendMessage(msg);
        	//BigTwoClient.numOfPlayers++;
        	BigTwoTable.existence++;
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		}
    	table.repaint();
    }
    
    /**
     * a method for parsing the messages received from the game server. 
     * This method should be called from the thread 
     * responsible for receiving messages from the game server. 
     * Based on the message type, different actions will be carried out
     */
    public synchronized void parseMessage(GameMessage message){
    	//System.out.println("print");
    	if (message.getType()==CardGameMessage.PLAYER_LIST){
    		table.printMsg("print");
    		playerID=message.getPlayerID();
    		table.setActivePlayer(playerID);
    		
    		table.printMsg(playerID+"");
    		table.printMsg("print");
    		
    		for (int i=0; i<numOfPlayers; i++){
    			if (((String[])message.getData())[i]==null) continue;
    			BigTwoClient.playerList.get(i).setName( ((String[])message.getData())[i]);
    		}
    	}
    	else if (message.getType()==CardGameMessage.JOIN){
    		//CardGamePlayer player = new CardGamePlayer((String)message.getData());
    		//playerList.add(player);
    		//this.playerID=message.getPlayerID();
    		//table.printMsg(playerID+"");
    		BigTwoClient.playerList.get(playerID).setName((String)message.getData());
    		//BigTwoClient.numOfPlayers=playerID+1;
    	}
    	else if (message.getType()==CardGameMessage.FULL){
    		table.printMsg("the server is full and cannot join the game");
    	}
    	else if (message.getType()==CardGameMessage.QUIT){
    		BigTwoClient.playerList.get(message.getPlayerID()).setName("");
    		//message=new CardGameMessage(CardGameMessage.JOIN,-1,null);
    		//sendMessage(message);
    		message=new CardGameMessage(CardGameMessage.READY,-1,null);
    		sendMessage(message);
    		//BigTwoClient.numOfPlayers--;
    		//System.exit(1);
    		BigTwoTable.existence--;
    	}
    	else if (message.getType()==CardGameMessage.READY){
    		table.printMsg("Player"+message.getPlayerID()+" is ready");
    		BigTwoTable.existence=message.getPlayerID()+1;
    		}
    	else if (message.getType()==CardGameMessage.START){
    		start((Deck)message.getData());
    	}
    	else if (message.getType()==CardGameMessage.MOVE){
    		checkMove(message.getPlayerID(),(int[])message.getData());
    	}
    	else if (message.getType()==CardGameMessage.MSG){
    		table.printMsg("Player"+message.getPlayerID()+":");
    		table.printMsg((String)message.getData());
    	}
    	table.repaint();
    }
    
    /**
     * a method for sending the specified message to the game server. 
     * This method should be called whenever the client wants to 
     * communicate with the game server or other clients.  
     */
    public synchronized void	sendMessage(GameMessage	message){
    	try {oos.writeObject(message);} 
    	catch (Exception ex) { ex.printStackTrace(); }
    		}
    
    /**
     * an inner class that implements the Runnable interface
     * @author CChen
     */
    class	ServerHandler implements Runnable{
    	 private ObjectInputStream	ois;
    	/**
    	 * create a thread with an instance of this class as its job in 
    	 * the makeConnection() method from the NetworkGame interface for 
    	 * receiving messages from the game server. Upon receiving a 
    	 * message, the parseMessage() method from the NetworkGame 
    	 * interface should be called to parse the messages accordingly.
    	 */
    	public void run(){
    		//if (endOfGame()) return;
    		try{
    			ois = new ObjectInputStream(sock.getInputStream());
        		Object msg;
    			while ((msg=ois.readObject())!=null){
    				//msg =  ois.readObject();
    				parseMessage((GameMessage) msg);
    				//GameMessage msgmsg=(GameMessage) msg;
    				//table.printMsg(msgmsg.getType()+"");
    				} 
    			}
    		catch (Exception ex) { ex.printStackTrace(); }
    	}
    }
    
    /**
     * a method for creating an instance of BigTwoClient
     * @param args
     */
    public static void	main(String[]	args){
    	BigTwoClient m = new BigTwoClient();
      }
    
    /**
     * check the end of game and create dialog
     * @param winner
     */
    public void endDialogue(int winner){
    	String s = new String("Game ends!\n");
    	for (int i=0; i<4; i++){
    		s+="player "+i+" has ";
    		if (i==winner){
    			s+="won the game\n";
    		}
    		else{
    			s+=getPlayerList().get(i).getCardsInHand().size()+" cards in hand\n";
    		}
    	}
    	 JDialog.setDefaultLookAndFeelDecorated(true);
    	    int response = JOptionPane.showConfirmDialog(null, s, "Confirm",
    	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    	    if (response == JOptionPane.NO_OPTION) {
    	      System.out.println("No button clicked");
    	    } else if (response == JOptionPane.YES_OPTION) {
    	    	CardGameMessage m = new CardGameMessage(CardGameMessage.READY,playerID,null);
    	    	sendMessage(m);
    	    	JOptionPane.getRootFrame().dispose(); 
    	    } else if (response == JOptionPane.CLOSED_OPTION) {
    	      table.printMsg("Not legal action");
    	    }  }
    }