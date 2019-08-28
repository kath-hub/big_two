import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
/**
 * The BigTwoTable class implements the CardGameTable interface. It is used to build a GUI for the Big Two card game and handle all user actions.
 * @author Lee Ka Fun
 *
 */
public class BigTwoTable implements CardGameTable {
	/**
	 * a constructor for creating a BigTwoTable. The parameter game is a reference to a card game associates with this table.
	 * @param game
	 */
	public BigTwoTable (CardGame game ) {
		this.game = game;
		
		//initialize the selected array
		selected = new boolean[13];
		for (int i = 0; i < 13; ++i) {
			selected[i]=false;
		}
		
		//Create the frame
		frame = new JFrame("Big Two");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(1400, 800)); 
		frame.setResizable(true);
		frame.setLayout(new GridLayout(1,2,0,0));
		
		//Create left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		//Create green area in left panel
		bigTwoPanel = new BigTwoPanel();
		leftPanel.add(bigTwoPanel);
		
		//Create Button Panel in left panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		playButton.setToolTipText("Select cards you want to play and press this button"); // A tool tip for the play button
		passButton = new JButton("Pass");		
		passButton.addActionListener(new PassButtonListener());
		passButton.setToolTipText("Select this button if you want to pass this round"); // A tool tip for the pass button
		buttonPanel.add(playButton);
		buttonPanel.add(passButton);
		buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonPanel.getMinimumSize().height));
        leftPanel.add(buttonPanel);
		
		//Create Menu Bar
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenuItem connect = new JMenuItem ("Connect");
		JMenuItem quit = new JMenuItem ("Quit");
		connect.addActionListener(new ConnectMenuItemListener());
		quit.addActionListener(new QuitMenuItemListener());
		gameMenu.add(connect);
		gameMenu.add(quit);
		menuBar.add(gameMenu);
		frame.setJMenuBar(menuBar);
		
		//Right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		// Create Message area with scroll bar;
		msgArea = new JTextArea();
		msgArea.setLayout(new FlowLayout(FlowLayout.CENTER));
		DefaultCaret caret1 = (DefaultCaret)msgArea.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		msgArea.setEditable(false);
		JScrollPane scroller = new JScrollPane(msgArea);
	    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// Create Chat Area
		chatArea = new JTextArea();
		chatArea.setLayout(new FlowLayout(FlowLayout.CENTER));
		DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		chatArea.setEditable(false);
		JScrollPane chatScroll = new JScrollPane(chatArea);
		chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Create input Area
		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.add(new JLabel("Message: "));
		inputArea = new JTextField(30);
		inputArea.addKeyListener(new EnterKeyListener());
		inputPanel.add(inputArea);
		
		enterButton = new JButton("Enter");
		enterButton.addActionListener(new EnterButtonListener());
		inputPanel.add(enterButton);
		
		inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputPanel.getMinimumSize().height));
		
		rightPanel.add(scroller);
		rightPanel.add(chatScroll);
		rightPanel.add(inputPanel);
			
		
		// Create Avatars Image 
		avatars = new Image[4];
		for (int i = 0; i < 4; ++i ) {
			avatars[i] = new ImageIcon(this.getClass().getResource("/p" + i + ".png")).getImage();
		}
		
		//Create Card Image 
		cardImages = new Image[4][13];
		for(int i=0;i<4;i++) {
			for(int j=0;j<13;j++) {
				cardImages[i][j]=  new ImageIcon(this.getClass().getResource("/" + i + "_" + j + ".png")).getImage();
			}
		}
		
		cardBackImage = new ImageIcon(this.getClass().getResource("cardback.png")).getImage();
		
		bkgImage = new ImageIcon(this.getClass().getResource("bkg.jpg")).getImage(); 
		
		
		frame.add(leftPanel);
		frame.add(rightPanel);	
		
		frame.setVisible(true);
		
			
	}
	
	private CardGame game; 

	private boolean[] selected;

	private int activePlayer;

	private JFrame frame;

	private JPanel bigTwoPanel;

	private JButton playButton;
	
	private JButton passButton;
	
	private JButton enterButton;

	private JTextArea msgArea ;
	
	private JTextArea chatArea ;
	
	private JTextField inputArea;
	
	private Image[][] cardImages;

	private Image cardBackImage;

	private Image[] avatars;
	
	private Image bkgImage;
	
	/**
	 * a method for setting the index of the active player (i.e., the current player)
	 */
	
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/**
	 * a method for getting an array of indices of the cards selected.
	 * @return an array of indices of the cards selected.
	 */
	
	public int[] getSelected() {
		ArrayList<Integer> selectedList = new ArrayList<Integer>();
		
		for (int i = 0; i < selected.length; ++i) {
			if (selected[i] == true) {
				selectedList.add(i);
			}
		}	
		
		int[] selectedIndex = new int[selectedList.size()];

		for (int i = 0; i < selectedList.size(); ++i) {
			selectedIndex[i]=selectedList.get(i).intValue();
		}
		return selectedIndex;	
	}
	
	/**
	 * a method for resetting the list of selected cards.
	 */
	
	public void resetSelected() {
		for (int i = 0; i < selected.length; ++i) {
			if (selected[i] == true) {
				selected[i] = false;
			}
		}	
	}
	/**
	 * a method for repainting the GUI.
	 */
	public void repaint() {
		bigTwoPanel.repaint();
		
	}
	
	/**
	 * a method for printing the specified string to the message area of the GUI.
	 */
	
	public void printMsg(String msg) {
		msgArea.append(msg+"\n");
	}
	
	/**
	 * a method for printing the specified string to the chat area of the GUI.
	 */
	public void printChatMsg(String msg) {
		chatArea.append(msg);
	}
	
	/**
	 * a method for clearing the message area of the GUI.
	 */
	
	public void clearMsgArea() {
		msgArea.removeAll();
		msgArea.setText(" ");
	}
	
	/**
	 * a method for resetting the GUI
	 */
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}
	
	/**
	 * a method for enabling user interactions with the GUI.
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}
	/**
	 * a method for disabling user interactions with the GUI.
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);	

		
	}
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface
	 * @author kat
	 *
	 */
	
	public class BigTwoPanel extends JPanel implements MouseListener{

		private static final long serialVersionUID = 1L;
		/**
		 * a public constructor that add mouse listener interface to this panel
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}
		
		/**
		 * a overriding method that from the MouseListener interface to handle mouse click events.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int y = 20;
			int mx = e.getX();
			int my = e.getY();
			
			int playerIndex = activePlayer;
			int numOfCards = game.getPlayerList().get(activePlayer).getNumOfCards();
			
			// card size = 69x105
			int yUpperBoundary ;
			int yLowerBoundary ;
			yUpperBoundary = y+ 20 + 105 + 140*playerIndex;
			yLowerBoundary = y+ 20 + 140*playerIndex;

			int xUpperBoundary = 200 + (numOfCards-1)*35 +69;	
			
			for (int i = 0; i < numOfCards; ++i) {
				int xUpperDoubleBoundary = 200 + (i+1)*35;	
				int xLowerBoundary = 200 + (i)*35;
				if (i != numOfCards-1) {
					if (selected[i] == false ) { // if the card clicked is not selected
						if (selected [i+1] == false) { // if the card on the right of the card clicked is not selected 
							if (my >= yLowerBoundary && yUpperBoundary > my && mx > xLowerBoundary && xUpperDoubleBoundary >= mx ) {
								selected[i] = true;
								}
						} else {// if the card on the right of the card clicked is selected
							if (my >= yLowerBoundary && yUpperBoundary >= my && mx > xLowerBoundary && xUpperDoubleBoundary+35 >= mx ) {
								if(my >= yLowerBoundary+95 && yUpperBoundary > my) {
									selected[i] = true;
								} else if (mx > xLowerBoundary && xUpperDoubleBoundary >= mx) {
									selected[i] = true;
								}
							}
									
						}
					} else { // if the card clicked is selected
						if (selected [i+1] == true) { // if the card on the right of the card clicked is selected
							if (my >= yLowerBoundary-10 && yUpperBoundary-10 > my && mx > xLowerBoundary && xUpperDoubleBoundary >= mx  ) {
								selected[i] = false;
								}
						} else { // if the card on the right of the card clicked is not selected 
							if (my >= yLowerBoundary-10 && yUpperBoundary-10 > my && mx > xLowerBoundary && xUpperDoubleBoundary+35 >= mx ) {
								if(my >= yLowerBoundary-10 && yUpperBoundary-10-95 > my) {
									selected[i] = false;
								} else if (mx > xLowerBoundary && xUpperDoubleBoundary >= mx) {
									selected[i] = false;
								}
							}	
							
						}
						}
				} else { // for the last card of the player's deck 
					if (selected[i] == false ) {
						if (my >= yLowerBoundary && yUpperBoundary >= my && mx >= xLowerBoundary && xUpperBoundary >= mx  ) {
							selected[i] = true;
							}
					} else {
						if (my >= yLowerBoundary-10 && yUpperBoundary-10 >= my && mx >= xLowerBoundary && xUpperBoundary >= mx  ) {
							selected[i] = false;
							}
						}
				}
			}
			
			frame.repaint();
		}
		/**
		 * A empty method
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * A empty method
		 */

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * A empty method
		 */

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * A empty method
		 */

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 *A method inherited from the JPanel class to draw the card game table
		 */
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			this.setBackground(Color.LIGHT_GRAY);
			
			int width = cardImages[1][1].getWidth(this);
			int height = cardImages[1][1].getHeight(this);
			
			int awidth = avatars[1].getWidth(this);
			int aheight = avatars[1].getHeight(this);
			
			int suit ;
			int rank;

			int y = 20;
			String name = null;
		    //Print the white brick background of the bigTwoPanel
			g.drawImage(bkgImage, 0, 0, bkgImage.getWidth(this)/2, bkgImage.getHeight(this)/2, this);
			
			for (int i = 0; i < 4; ++i) {
				name = game.getPlayerList().get(i).getName();
				if (name != null && name != "" ) {
					g.drawString(name, 50 , y+10+140*i);
					g.drawImage(avatars[i], 10, y+140*i, awidth/2, aheight/2, this);			
				}
			}
				
				//Print Players' cards
				for ( int i = 0; i < 4 ; ++ i ) {
					if (i == activePlayer) { // Print front side of the card  of the active player
						for(int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); ++j)
						{
							suit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
							rank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
							
							if(selected[j] == true) // if the card is selected, it will be elevated
							{
								g.drawImage(cardImages[suit][rank], j*35+200, y+10+140*i,70, height/10, this);
							}
							else // if the card is not selected, it will not be elevated
							{
								g.drawImage(cardImages[suit][rank], j*35+200, y+20+140*i,70, height/10, this);
							}
						}
						
					} else {
						for(int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); ++j){ // for non-active players's cards, print out card back only
							g.drawImage(cardBackImage, j*35+200, y+20+140*i, 70, height/10, this); // Print a crown for the winner
							//g.drawImage(cardBackImage, j*35+200, y+20+140*i, width/10, height/10, this);
						}
					}
					
					g.setColor(Color.darkGray);
					g.drawLine(0, 145+145*i, 800,  145+145*i);
					

				}
			
			//Print Last hand played
			int tableHandSize = game.getHandsOnTable().size();
			if (tableHandSize > 0 ) {
				String playby= game.getHandsOnTable().get(tableHandSize-1).getPlayer().getName();
				int playbyindex = -1;
				for(int i = 0; i < 4; i++){
					if(game.getPlayerList().get(i).getName() == playby){
						playbyindex = i;
					}
				}
				g.drawString("Played by " + playby, 30, y-10+140*4 + 25);
				g.drawImage(avatars[playbyindex], 10, y+140*4, awidth/2, aheight/2, this);
				for(int i = 0; i < game.getHandsOnTable().get(tableHandSize - 1).size(); i++){
					suit = game.getHandsOnTable().get(tableHandSize - 1).getCard(i).getSuit();
					rank = game.getHandsOnTable().get(tableHandSize - 1).getCard(i).getRank();
					g.drawImage(cardImages[suit][rank],   i*30+200, y+20+140*4,width/10, height/10, this);
				}
				
			}
			
		}

	}
	
	
	private class PlayButtonListener implements ActionListener {
		/**
		 * A method from the ActionListener interface to handle button-click events for the “Play” button
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (getSelected().length < 1) {
				printMsg("Select cards to play."); // if Player submit a empty hand without using Pass button 
			}else {
				if(game.getCurrentIdx() == ((BigTwoClient)game).getPlayerID()){
					game.makeMove(game.getCurrentIdx(), getSelected());
					}
			}
		}	
	}
	
	private class PassButtonListener implements ActionListener{
		/**
		 * A method from the ActionListener interface to handle button-click events for the “Pass” button
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (game.getHandsOnTable().isEmpty()==true) { // if the first move is a "Pass"
				printMsg("Select cards to start the game.");
				JOptionPane.showMessageDialog(frame, "Select cards to start the game.", "Watch Out", JOptionPane.WARNING_MESSAGE);
			}else {
				if(game.getCurrentIdx() == ((BigTwoClient)game).getPlayerID()){
					game.makeMove(game.getCurrentIdx(), null);
					}
			}
		}	
	}
	
	
	private class EnterButtonListener implements ActionListener{
		/**
		 * A method from the ActionListener interface to handle button-click events for the “Enter” button
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String input = inputArea.getText();
			((BigTwoClient)game).sendMessage(new CardGameMessage(7, -1, input));
			inputArea.setText("");

		}	
	}
	
	private class EnterKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * A method from KeyListner when the enter key is pressed
		 */

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				String input = inputArea.getText();
				((BigTwoClient)game).sendMessage(new CardGameMessage(7, -1, input));
				inputArea.setText("");
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class ConnectMenuItemListener implements ActionListener{
		/**
		 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Restart” menu item.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String tablePlayerName = null;
			while(((BigTwoClient)game).getPlayerName()== null) {
				tablePlayerName = JOptionPane.showInputDialog(frame, "Input Your Name");
				if(tablePlayerName != null && tablePlayerName != "") {
					((BigTwoClient)game).setPlayerName(tablePlayerName);
				} else {
					System.exit(0);
				}
			}
			
	     	((BigTwoClient)game).setServerIP("127.0.0.1");
	 		((BigTwoClient)game).setServerPort(2396);
	 		((BigTwoClient)game).makeConnection();
					
		}
	}

	private class QuitMenuItemListener implements ActionListener{
		/**
		 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(activePlayer);
		}
	}
}
