import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * The BigTwoClient class implements the CardGame interface and NetworkGame interface. It
 * is used to model a Big Two card game that supports 4 players playing over the internet.
Below is a detailed description for the BigTwoClient class
 * @author kat
 *
 */
public class BigTwoClient implements CardGame, NetworkGame{
	/**
	 * a constructor for creating a Big Two client
	 */
	public BigTwoClient(){
		playerList = new ArrayList<CardGamePlayer>();
		handsOnTable=new ArrayList<Hand>();
		
		CardGamePlayer a = new CardGamePlayer();
		CardGamePlayer b = new CardGamePlayer();
		CardGamePlayer c = new CardGamePlayer();
		CardGamePlayer d = new CardGamePlayer();
		playerList.add(a);
		playerList.add(b);
		playerList.add(c);
		playerList.add(d);
		
		table = new BigTwoTable(this);
		table.repaint();
		
		makeConnection();		
	}
	
	private int numOfPlayers;
	
	private Deck deck;
	
	private ArrayList<CardGamePlayer> playerList;
	
	private ArrayList<Hand> handsOnTable;
	
	private int playerID;
	
	private String playerName;	
	
	private String serverIP;
	
	private int serverPort;
	
	private Socket sock;
	
	private ObjectOutputStream oos;
	
	private int currentIdx;
	
	private BigTwoTable table;
	
	/**
	 * a method for getting the number of players.
	 * @return number of players.
	 */
	@Override
	public int getNumOfPlayers() {
		// TODO Auto-generated method stub
		return this.numOfPlayers;
	}

	/**
	 * a method for getting the deck of cards being used.
	 * @return the deck of cards being used
	 */
	@Override
	public Deck getDeck() {
		// TODO Auto-generated method stub
		return this.deck;
	}

	/**
	 * a method for getting the list of players
	 * @return the list of players
	 */
	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		// TODO Auto-generated method stub
		return this.playerList;
	}

	/**
	 * a method for getting the list of hands played on the table
	 * @return the list of hands played on the table
	 */
	@Override
	public ArrayList<Hand> getHandsOnTable() {
		// TODO Auto-generated method stub
		return this.handsOnTable;
	}

	/**
	 * a method for getting the index of the player for the current turn
	 * @return the index of the player for the current turn
	 */
	@Override
	public int getCurrentIdx() {
		// TODO Auto-generated method stub
		return this.currentIdx;

	}

	/**
	 * a method for starting/restarting the game with a given shuffled deck of cards
	 */
	@Override
	public void start(Deck deck) {
		// TODO Auto-generated method stub
		
		//Remove all cards from all players and table
		for (int i = 0; i<4; ++i ) {
			playerList.get(i).removeAllCards();;
		}
		
		handsOnTable.clear();
		
		//Distribute the card to players
		for (int i = 0; i< 4; ++i) {
			for (int j = 0; j < 13; ++j) {
				Card card = deck.getCard(0);
				playerList.get(i).addCard(card);
				deck.removeCard(0);
			}
		}
		
		for (int i = 0; i<4; ++i ) {
			playerList.get(i).sortCardsInHand();
		}
		
		//Identify the holder of diamond of three 
		currentIdx = -1;
		BigTwoCard bigthreediamond = new BigTwoCard(0, 2);
		
		for (int i = 0; i<4; ++i ) {
			if (playerList.get(i).getCardsInHand().contains(bigthreediamond)) {
				currentIdx = i;	// Set currentIdx of BigTwoClient
				
			}
		} 
		
		//Set activePlayer of the BigTwoTable instance
		table.printMsg(playerList.get(currentIdx).getName() + "'s turn:\n");		
	}

	/**
	 * a method for making a move by a player with the specified playerID using the cards specified by the list of indices. This method should be called from the BigTwoTable when the local player presses either the
“Play” or “Pass” button
	 */
	@Override
	public void makeMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		
		try {
			CardGameMessage moveMessage=new CardGameMessage(6, -1, cardIdx);
			sendMessage(moveMessage);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * a method for checking a move made by a player.
	 */
	@Override
	public void checkMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		
		currentIdx=playerID;
		Card threeDiamond = new Card(0,2);
		Hand currentHand = null;

		CardList selectedCards = playerList.get(playerID).play(cardIdx);
		
		boolean legalMove=true;
		
		//For the first hand of the game
		if(handsOnTable.isEmpty() ==true) {
				if (selectedCards.contains(threeDiamond)) { 
					currentHand = composeHand(playerList.get(currentIdx),selectedCards );
					if(currentHand !=null) {
						playerList.get(currentIdx).removeCards(selectedCards );
						handsOnTable.add(currentHand );
						table.printMsg(currentHand .getPlayer().getName());
						table.printMsg(currentHand .getType());
						table.printMsg(currentHand .toString());
					} else { // This first hand contains three of diamond but not a legal hand
						table.printMsg("Not a Legal Move!");
						legalMove=false;
					}
				} else {
					table.printMsg("Not a Legal Move!");
					legalMove=false;
				}
	
		} else if(playerList.get(playerID).getNumOfCards()>0) {	 // after the first move, if there is no winner
		    	boolean proceed = false;
				if( proceed ==false) {
					selectedCards  = playerList.get(currentIdx).play(cardIdx);

					// When a player presses pass button
					if(selectedCards == null) {
						if(handsOnTable.get(0).getPlayer()==playerList.get(currentIdx)) { // If the active player and the player of the last hand is the same, the player cannot pass
							
							table.printMsg("Not a legal move!");
							legalMove=false;
							
						} else {
							
							table.printMsg(playerList.get(playerID).getName());
							table.printMsg("{pass}");
							proceed =true;
							
						}
						
					} else { // when the hand submitted is not null 
						
						currentHand = composeHand(playerList.get(currentIdx),selectedCards );
						//current player = player of last hand
						if(currentHand != null && handsOnTable.get(0).getPlayer()==playerList.get(currentIdx)){
							handsOnTable.add(currentHand);
							table.printMsg(currentHand .getPlayer().getName());
							table.printMsg(currentHand .getType());
							table.printMsg(currentHand .toString());
							playerList.get(currentIdx).removeCards(selectedCards);
							handsOnTable.remove(0);
							
							if (endOfGame() == true) {
								proceed = false;
							    legalMove = false;
							} else {
								proceed = true;
							}
						} else {
							if(currentHand != null && currentHand.beats(handsOnTable.get(0))){
							handsOnTable.add(currentHand );
							table.printMsg(currentHand .getPlayer().getName());
							table.printMsg(currentHand .getType());
							table.printMsg(currentHand .toString());
							playerList.get(currentIdx).removeCards(selectedCards );
							handsOnTable.remove(0);
							if (endOfGame() == true) {
								proceed = false;
								legalMove = false;
							} else {
								proceed = true;
							}
						} else { // if hand submitted through play button is null 
							table.printMsg("Not a legal move!");
							legalMove=false;
							}
							}
						}
					}
				}
		
		if(endOfGame()) {
			table.repaint();
			table.disable();
			table.printMsg("Game ends");
			String optionPaneMsg="Game ends \n";
			
			//Generating game result 
			for(int i=0; i<4; i++) {
				if(currentIdx==i) {
					table.printMsg(playerList.get(currentIdx).getName()+" wins the game.");
					optionPaneMsg+=playerList.get(currentIdx).getName()+" wins the game. \n";
				}
				else {
					optionPaneMsg+=playerList.get(i).getName()+" has "+playerList.get(i).getNumOfCards()+" cards in hand. \n";
					table.printMsg(playerList.get(i).getName()+" has "+playerList.get(i).getNumOfCards()+" cards in hand.");
				}
			}
			//Dialog box for game result
			JOptionPane.showMessageDialog(null, optionPaneMsg, "Game Over!", JOptionPane.PLAIN_MESSAGE);
			
			//When dialog box is closed, send a ready message
			sendMessage(new CardGameMessage(4, -1, null));
			
		}

		
		if(legalMove==true) {
			++currentIdx;
			if(currentIdx > 3) {
				currentIdx =0 ;
			}
					
		} else {
			table.enable();
		}
		
		table.resetSelected();
		table.printMsg(this.getPlayerList().get(currentIdx).getName() + "'s turn");
		table.repaint();	
	}

	/**
	 * a method for checking if the game ends.
	 * @return a boolean value that reflects whether the game has ended
	 */
	@Override
	public boolean endOfGame() {
		// TODO Auto-generated method stub
		if(playerList.get(currentIdx).getNumOfCards()==0){		
			return true;
		}
		else {
			return false;
		}
	}
	
	//network game interface
	
	/**
	 * a method for getting the playerID (i.e., index) of the local player.
	 * @return the playerID (i.e., index) of the local player
	 */
	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return this.playerID;
	}

	/**
	 * a method for setting the playerID (i.e., index) of
the local player. This method should be called from the parseMessage() method when a
message of the type PLAYER_LIST is received from the game server.
@param int playerID
	 */
	@Override
	public void setPlayerID(int playerID) {
		// TODO Auto-generated method stub
		this.playerID=playerID;
	}

	/**
	 * a method for getting the name of the local player.
	 * @return the name of the local player. 
	 */
	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return this.playerName;
	}
	
	/**
	 * a method for setting the name of the local player.
	 * @param String playerName
	 */
	@Override
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		this.playerName=playerName;
		
	}

	/**
	 * a method for getting the IP address of the game server.
	 * @return the IP address of the game server
	 */
	@Override
	public String getServerIP() {
		// TODO Auto-generated method stub
		return this.serverIP;
	}

	/**
	 * a method for setting the IP address of the game server
	 * @param String serverIP
	 */
	@Override
	public void setServerIP(String serverIP) {
		// TODO Auto-generated method stub
		this.serverIP=serverIP;
		
	}

	/**
	 * a method for getting the TCP port of the game server.
	 * @return the TCP port of the game server.
	 */
	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return this.serverPort;
	}

	/**
	 * a method for setting the TCP port of the game server
	 * @param int serverPort
	 */
	@Override
	public void setServerPort(int serverPort) {
		// TODO Auto-generated method stub
		this.serverPort=serverPort;
		
	}
	
	/**
	 * a method for making a socket connection with the game server
	 */
	@Override
	public void makeConnection() {
		// TODO Auto-generated method stub

		while (this.getPlayerName()==null || this.getPlayerName()=="") {
			String name = JOptionPane.showInputDialog("Input your name:");
			setPlayerName(name);
		}
		
		while (this.getServerIP()==null || this.getServerIP()=="") {
			String serverIP = JOptionPane.showInputDialog("IP address:", "127.0.0.1");
			setServerIP(serverIP);
		}
		
		int serverPort = Integer.parseInt(JOptionPane.showInputDialog("TCP port:", "2396"));
		setServerPort(serverPort);

		
		try {		
		sock = new Socket("127.0.0.1", 2396);		
		//create an ObjectOutputStream for sending messages to the game server
		oos = new ObjectOutputStream(sock.getOutputStream());
		//create a thread for receiving messages from the game server
		Thread thread = new Thread(new ServerHandler());
		thread.start();
		//send a message of the type JOIN to the game server
		sendMessage(new CardGameMessage(1, -1, this.getPlayerName()));
		//send a message of the type READY to the game server
		sendMessage(new CardGameMessage(4, -1, null));
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * a method for parsing the messages received from the game server.
	 * @param GameMessage message
	 */
	@Override
	public synchronized void parseMessage(GameMessage message) {
		// TODO Auto-generated method stub
		
		//PLAYER_LIST
		if(message.getType()==0) {
			this.setPlayerID(message.getPlayerID());
			table.setActivePlayer(message.getPlayerID());
			String[] playerNames = (String[])message.getData();
			for(int i=0; i<4; i++) {
				playerList.get(i).setName(playerNames[i]);
			}
			table.repaint();

		}
		
		//JOIN
		if(message.getType()==1) {
			playerList.get(message.getPlayerID()).setName((String)message.getData());
			table.repaint();
		}
		
		//FULL
		if(message.getType()==2) {
			table.printMsg("The server is full and cannot join the game");
		}
		
		//QUIT
		if(message.getType()==3) {
			playerList.get(message.getPlayerID()).setName(null);
			table.disable();
			table.printMsg("Player "+message.getPlayerID()+" left the game");
			sendMessage(new CardGameMessage(4, -1, null));
			table.repaint();
		}
		
		//READY
		if(message.getType()==4) {
			table.printMsg("Player "+ message.getPlayerID()+ " " + playerList.get(message.getPlayerID()).getName() +" is ready.");
			table.repaint();
		}
		
		//START
		if(message.getType()==5) {
			table.reset();
			table.printMsg("All players are ready. Game Starts");
			this.start((BigTwoDeck)message.getData());
			table.repaint();
		}
		
		//MOVE
		if(message.getType()==6) {
			 checkMove(message.getPlayerID(), (int[])message.getData());
		}
		//MSG
		if(message.getType()==7) {
			table.printChatMsg(message.getData().toString() + "\n");
		}
				
	}

	/**
	 * a method for sending the specified message to the game server
	 * @param GameMessage message
	 */
	@Override
	public void sendMessage(GameMessage message) {
		// TODO Auto-generated method stub
		try 
		{
			oos.writeObject(message);
			oos.flush();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	private class ServerHandler implements Runnable{
		/**
		 * Upon receiving a
message, the parseMessage() method from the NetworkGame interface should be
called to parse the messages accordingly.
		 */
		
		public void run() {
			CardGameMessage message;
			try {
				ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
				while (true) {
					message=(CardGameMessage)ois.readObject();
					parseMessage(message);
				} 
			} catch (EOFException ei) {
				
			} catch (SocketException eii) {
				eii.printStackTrace();
			} catch (Exception eiii) {
				eiii.printStackTrace();
			}			
		}		
		
	}
	/**
	 * a method for creating an instance of BigTwoClient
	 * @param args
	 */
	public static void main (String[] args) {
		BigTwoClient BigTwoGame = new BigTwoClient();			
		}
	
	/**
	 * a method for
	returning a valid hand from the specified list of cards of the player. Returns null is no
	valid hand can be composed from the specified list of cards.
	 * @param player
	 * @param cards
	 * @return a valid hand from the specified list of cards of the player
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
			
			Single single = new Single(player, cards);
			if (single.isValid()) {
				return single;
			}
			
			Pair pair = new Pair(player, cards);
			if (pair.isValid()) {
				return pair;
			}
			
			Triple triple = new Triple(player, cards);
			if (triple.isValid()) {
				return triple;
			}
			
			StraightFlush straightflush = new StraightFlush(player, cards);
			if (straightflush.isValid()) { 
				return straightflush;
			}
			
			Straight straight = new Straight(player, cards);
			if (straight.isValid()) {
				return straight;
			}
			
			Flush flush = new Flush(player, cards);
			if (flush.isValid()) {
				return flush;
			}
			
			FullHouse fullhouse = new FullHouse(player, cards);
			if (fullhouse.isValid()) {
				return fullhouse;
			}
			
			Quad quad = new Quad(player, cards);
			if (quad.isValid()) {
				return quad;
			}
			
			return null;				
		
		}	

}
