
/**
 * <h1>this is the BigTwoTable class!</h1>
 * The class will deal with the user interactions
 * and display things in the frame
 * <p>
 * @author: CChen
 * @version: 1.0
 * @since: 2016-11-14
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

public class BigTwoTable implements CardGameTable{
	
	/**
	 * The constructor of the object of the class
	 * The constructor will initialize the panels, menu,
	 * text field and buttons in the same time
	 */
	public BigTwoTable(BigTwoClient game){
		this.game= game;
		activePlayer=game.getCurrentIdx();
		resetSelected();
		frame = new JFrame();
		frame.setTitle("                  "+
		"                        Big Two");
		frame.setSize(1000,	750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bigTwoPanel = new BigTwoPanel();
	bigTwoPanel.setForeground(Color.blue);
		frame.add(bigTwoPanel,BorderLayout.CENTER);
		
		playButton = new JButton("              "+
		"PLAY"+"             ");
		
		bigTwoPanel.add(playButton);
		
		passButton = new JButton("              "
				+"PASS"+"                ");
			
		bigTwoPanel.add(passButton);
		playButton.addActionListener(new PlayButtonListener());
		playButton.setBackground(Color.green);
		playButton.setFont(new	Font("Franklin Gothic Demi",Font.PLAIN,9));
		passButton.addActionListener(new PassButtonListener());
		passButton.setBackground(Color.red);
		passButton.setFont(new	Font("Franklin Gothic Demi",Font.PLAIN,9));
		bigTwoPanel.addMouseListener(new BigTwoMouse());

		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game");
		JMenuItem connect = new JMenuItem("Connect");
		JMenuItem quit = new JMenuItem("Quit");
		menu.add(connect);
		menu.add(quit);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		quit.addActionListener(new QuitMenuItemListener());
		connect.addActionListener(new ConnectMenuItemListener());
		
		JPanel panel = new JPanel(new BorderLayout());
		msgArea1 = new JTextArea(20,20);
		msgArea1.setBackground(Color.WHITE);
		JScrollPane scroller = new JScrollPane(msgArea1);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel.add(scroller,BorderLayout.NORTH);
		msgArea2 = new JTextArea(20,20);
		panel.add(msgArea2,BorderLayout.SOUTH);
		frame.add(panel,BorderLayout.EAST);
		field = new JTextField(20);
		field.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                  CardGameMessage m = new CardGameMessage
                		  (CardGameMessage.MSG,game.getPlayerID(),field.getText());
                  game.sendMessage(m);
                  field.setText("");
        }
		});
		frame.add(field,BorderLayout.SOUTH);
		printMsg("                                    ");
		cardImages = new Image[13][4];
		for  (int i=1; i<14; i++){
			for (int j=0; j<4; j++){
				if (i>=2 && i<10)
				cardImages[i-1][j]=new ImageIcon("images/"
				+i+suits[j]+".gif").getImage();
				else if (i==1) cardImages[i-1][j]
						=new ImageIcon("images/a"+suits[j]+".gif").getImage();
				else if (i==10) cardImages[i-1][j]
						=new ImageIcon("images/t"+suits[j]+".gif").getImage();
				else if (i==11) cardImages[i-1][j]
						=new ImageIcon("images/j"+suits[j]+".gif").getImage();
				else if (i==12) cardImages[i-1][j]
						=new ImageIcon("images/q"+suits[j]+".gif").getImage();
				else if (i==13) cardImages[i-1][j]
						=new ImageIcon("images/k"+suits[j]+".gif").getImage();
			}
		}
		cardBackImage = new ImageIcon("images/b.gif").getImage();
	avatars = new Image[4];
	avatars[0] = new ImageIcon("images/acchan.png").getImage();
	avatars[1] = new ImageIcon("images/takamina.png").getImage();
	avatars[2] = new ImageIcon("images/mayuyu.png").getImage();
	avatars[3] = new ImageIcon("images/yuko.png").getImage();
	    bigTwoPanel.setVisible(true);
	    panel.setVisible(true);
		frame.setVisible(true);	
		enable();
		inputDialog();
	}
	
	private final String suits[]={"d","c","h","s"};
	private BigTwoClient game;
	private boolean[] selected = new boolean[13];
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea1;
	private JTextArea msgArea2;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
    private JMenuBar menuBar;
    private JTextField field;
    
    public Hand a;
    private boolean isFirst=true;
    public boolean legal=true;
    private double bili=1.0;
    private int cheight=(int)Math.round(95*bili);
    private int cwidth=(int)Math.round(70*bili);
    private int iconwidth=(int)Math.round(70*bili);
    private int iconheight=(int)Math.round(70*bili);
    private double front=100*bili;
    private double diff=37*bili;
    public static int existence=0;

    private void inputDialog(){
    	game.setPlayerName(JOptionPane.showInputDialog
    			("Enter your name:","Player 0"));
    }
    
    private void conditioner(){
    	if ((bigTwoPanel.getWidth()/746.0)>(bigTwoPanel.getHeight()/688.0))
    		bili=bigTwoPanel.getHeight()/688.0;
    	else bili=bigTwoPanel.getWidth()/746.0;
    	
    	front=100*bili;
      diff=37*bili;
    	
       cheight=(int)Math.round(95*bili);
       cwidth=(int)Math.round(70*bili);
       iconwidth=(int)Math.round(70*bili);
       iconheight=(int)Math.round(70*bili);
    }
    
    /**
     * This set The active player to be the value that's
     * specified in the argument. If the argument contains
     * unacceptable value, by default it will initialize
     * the active player as -1
     * @param activePlayer int
     */
    public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= 4) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
	}
	
    /**
     * This method is for getting the index of activePlayer
     * @return int activePlayer
     */
    public int getActivePlayer(){return activePlayer;}
    
    /**
     * This method is for getting which cards are selected
     * by user.
     * @return the array that specifies the indexes of 
     * the chosen cards
     */
	public int[] getSelected(){
			int[] cardIdx = null;
			int count = 0;
			for (int j = 0; j < game.getPlayerList().get(activePlayer).getCardsInHand().size(); j++) {
				if (selected[j]) {
					count++;
				}
			}
			if (count != 0) {
				cardIdx = new int[count];
				count = 0;
				for (int j = 0; j < game.getPlayerList().get(activePlayer).getCardsInHand().size(); j++) {
					if (selected[j]&&count<cardIdx.length) {
						cardIdx[count] = j;
						count++;
					}
				}
			}
			return cardIdx;
		}
	
	/**
	 * The selected array will be initialized by
	 * default value which is false 
	 */
	public void resetSelected(){
		for (int i=0; i<selected.length; i++){
			selected[i]=false;
		}
	}
	
	/**
	 * The method  to repaint  the panel
	 */
	public void repaint() {
		frame.repaint();
		
	}
	
    /**
     * print message to the text field
     * @param msg String
     */
    public void printMsg(String msg){
    	msgArea1.append(msg+"                         "
    +"                       "+'\n');
    }
    
    /**
     * clear the message area
     */
    public void clearMsgArea(){
    	msgArea1.setText("");
    }
    
    /**
     * reset the frame by resetting the selected array
     * and initialize the activePlayer integer by setting
     * its value as -1
     */
    public void reset(){
    	resetSelected();
    	activePlayer=-1;
    	clearMsgArea();
    	this.a=null;
    	this.legal=true;
    }
    
    /**
     * enable user interaction
     */
    public void enable(){
    	frame.setEnabled(true);
    }
    
    /**
     * disable user interaction
     */
    public void disable(){
    	bigTwoPanel.setEnabled(false);
    }
    
    private int toInt(double i){
    	return (int) Math.round(i);
    }
    
    /**
     * The inner class dealing with paintComponent() and 
     * mouseClicked(), other events relating to mouse
     * are ignored
     * @author CChen
     * @version 1.0
     * @since  2016-11-14
     */
    class BigTwoPanel extends JPanel implements MouseListener{
    	
    	/**
    	 * The method to paint the components of the frame
    	 * @param g Graphics
    	 */
    	public void paintComponent(Graphics g){
    		conditioner();
    		//if (isFirst) return;
    		if (activePlayer!=BigTwoClient.currentIdx) {
    			playButton.setEnabled(false); passButton.setEnabled(false);
    		}
    		Font bigFont	=	new	Font("Franklin Gothic Demi",Font.ITALIC,toInt(14*bili));	
    		g.setFont(bigFont);	
    		g.setColor(Color.blue);
    		//printMsg(BigTwoClient.numOfPlayers+"players");
    		for (int i=0; i<existence; i++){
    			g.drawLine(1, toInt((147+120*i)*bili), frame.getWidth()-20, toInt((147+120*i)*bili));
    			if (i==activePlayer){
    				g.setColor(Color.ORANGE);
    			g.drawString(game.getPlayerList().get(i).getName(), toInt(5*bili), toInt((140+120*i)*bili));
    			g.setColor(Color.blue);}
    			else{
    				g.drawString(game.getPlayerList().get(i).getName(), toInt(5*bili), toInt((140+120*i)*bili));
    			}
    		}
    		int suit;
    		int value;
    		
    	    if (!isFirst){
    	    	g.drawString("Last player: ", toInt(10*bili),toInt(525*bili));
    	    	g.setColor(Color.MAGENTA);
    	    	g.drawString(game.getPlayerList().get(BigTwoClient.last).getName(), toInt(100*bili),toInt(525*bili));
    	        g.setColor(Color.blue);
    	    	g.drawImage(avatars[BigTwoClient.last], toInt(10*bili),toInt(535*bili), iconwidth,iconheight,this);
    	    }
    		
    	    if (BigTwoClient.lasthand!=null){
    	    	Hand lastHand=game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
    	    	if (lastHand!=null){
    		  for (int i=0; i<lastHand.size(); i++ ){
    			  value=lastHand.getCard(i).getRank();
    			  suit=lastHand.getCard(i).getSuit();
    			  g.drawImage(cardImages[value][suit], toInt((front+diff*i)), toInt(540*bili), cwidth,cheight,this);
    		  }}
    		}
    	    
    	    else{
    	    	g.drawString("The table is still empty!", toInt(10*bili), toInt(bili*545));
    	    }
    		for (int i=0; i<BigTwoTable.existence; i++){
    		g.drawImage(avatars[i], -5, toInt((50+120*i)*bili),iconwidth,iconheight, this);
    		}
    		Card temp;
    		for (int i=0; i<BigTwoTable.existence; i++){
    			if (activePlayer==i){
    			for (int j=0; j<game.getPlayerList().get(i).getCardsInHand().size(); j++){
    				temp=game.getPlayerList().get(i).getCardsInHand().getCard(j);
    				if (temp!=null){
    				suit=temp.getSuit();
    				value=temp.getRank();
    				if (selected[j]){
    					g.drawImage(cardImages[value][suit], toInt((front+diff*j)), toInt((30+120*i)*bili),cwidth,cheight, this);
    				}
    				else g.drawImage(cardImages[value][suit],toInt((front+diff*j)), toInt((50+120*i)*bili),cwidth,cheight,this);}}
    			}
    			else{
    				if (!game.endOfGame()){
    				for (int j=0; j<game.getPlayerList().get(i).getCardsInHand().size(); j++){
    					g.drawImage(cardBackImage,toInt((front+diff*j)), toInt((50+120*i)*bili),cwidth,cheight,this);
    				}}
    				else{
    					for (int j=0; j<game.getPlayerList().get(i).getCardsInHand().size(); j++){
    	    				temp=game.getPlayerList().get(i).getCardsInHand().getCard(j);
    	    				if (temp!=null){
    	    				suit=temp.getSuit();
    	    				value=temp.getRank();
    	    				g.drawImage(cardImages[value][suit],toInt((front+diff*j)), toInt((50+120*i)*bili),cwidth,cheight,this);}}
    					    bigFont = new	Font("Franklin Gothic Demi",Font.BOLD,toInt(40*bili));	
    					    g.setFont(bigFont);
    					    g.setColor(Color.YELLOW);
    					    g.drawString("Game ends!", 40, 120);
    					    g.setColor(Color.PINK);
    					    g.drawString("Player"+activePlayer, 40, 200);
    					    g.drawString(" has won the game!", 40, 250);
    					    game.endDialogue(activePlayer);
    				}
    				}
    		}
    	}

    	/**
    	 * all the methods below relating to mouse are
    	 * useless and ignored
    	 */
    	public void mouseClicked(MouseEvent event){}
    	public void mousePressed(MouseEvent event){}
    	public void mouseExited(MouseEvent event){}
    	public void mouseEntered(MouseEvent event){}
    	public void mouseReleased(MouseEvent event){}
    }

/**
 * <h1>This class is used to deal with mouseEvent</h1>
 * <p>
 * @author CChen
 * @since 2016-11-14
 * @version 1.0
 */
 class BigTwoMouse extends MouseAdapter{
	 double x;
	 double y;
	 
	 /**
 	 * the method to deal with the actions when mouse is clicked
 	 * @param event MouseEvent
 	 */
	 public void mouseClicked(MouseEvent event){
		 conditioner();
 		x = event.getX();
 		y = event.getY();
 		//printMsg(x+" "+y);
 		if ((int) Math.floor((y/bili-30)/120.0) != activePlayer ||
 				x<front || x>front+game.getPlayerList().get(activePlayer)
 				.getCardsInHand().size()*diff+47*bili) return;
 		
 		int select=whichcard(x);
 		if (select<0) return;
 		else if (select==0){
 			if (selected[select]) selected[select]=false;
 	 		else selected[select]=true;
 			repaint();
 			return;
 		}
 		else if (selected[select-1]==false&&selected[select])
 			select=whichFT(x,y);
 		else if (selected[select-1]&&!selected[select])
 			select=whichTF(x,y);
 		else if (selected[select-1]&&selected[select])
 				select=whichTT(x,y);
 		else select=whichFF(x,y);
 		if (select<0||select>=game.getPlayerList().get(activePlayer).
 				getCardsInHand().size()) return;
 		if (selected[select]) selected[select]=false;
 		else selected[select]=true;
 		repaint();
 	}
	 
	 /**
 	 * the method to decide which card is chosen
 	 * @param x the x-coordinate
 	 * @param y the y-coordinate
 	 * @return the index of the card chosen
 	 */
	 private int whichcard(double x){
 		int i=(int) Math.floor((x/bili-100)/37.0);
 		if (i>=0&&i<game.getPlayerList().get(activePlayer).getCardsInHand().size())
 		return i;
 		else if (i<0) return -1;
 		else  return game.getPlayerList().get(activePlayer).getCardsInHand().size()-1;
 	}
	 
	 private int whichFT(double x,double y){
		 if ((int) Math.floor((y/bili-30)/120.0) != activePlayer ||
	 				x<front || x>front+game.getPlayerList().get(activePlayer)
	 				.getCardsInHand().size()*diff+47*bili) return -1;
		 if (y/bili>50+120*activePlayer+72) return whichcard(x)-1;
		 else return whichcard(x);
	 }
	 
	 private int whichTF(double x,double y){
		 if ((int) Math.floor((y/bili-30)/120.0) != activePlayer ||
	 				x<front || x>front+game.getPlayerList().get(activePlayer)
	 				.getCardsInHand().size()*diff+47*bili) return -1;
		 if (y/bili<50+120*activePlayer) return whichcard(x)-1;
		 else return whichcard(x);
	 }
	 
	 private int whichTT(double x,double y){
		 if ((int) Math.floor((y/bili-30)/120.0) != activePlayer ||
	 				x<front || x>front+game.getPlayerList().get(activePlayer)
	 				.getCardsInHand().size()*diff+47*bili) return -1;
		 return whichcard(x);
	 }
	 
	 private int whichFF(double x,double y){
		 if ((int) Math.floor((y/bili-50)/120.0) != activePlayer ||
	 				x<front || x>front+game.getPlayerList().get(activePlayer)
	 				.getCardsInHand().size()*diff+47*bili) return -1;
		 return whichcard(x);
	 }
    }
 
    /**
     * playButton listener to deal with actions
     * when user push the play button
     * @author CChen
     * @version 1.0
     * @since 2016-11-14
     */
    class PlayButtonListener implements ActionListener{
    	
    	/**
    	 * deal with actions when playButton is pushed
    	 */
    	public void actionPerformed(ActionEvent event){
    		game.makeMove(activePlayer, getSelected());
    		//game.checkMove(activePlayer,getSelected());
    		if (!legal) {
    			resetSelected();
    			repaint();
    			printMsg("Please choose again!");
    			 printMsg("Player"+activePlayer+"'s turn");
    			return;
    		}
    		if  (a!=null){
    		printMsg("{"+a.getType()+"} ");
    		print();
    		isFirst=false;}
    		
    		if  (getSelected()!=null){
    			game.getPlayerList().get(activePlayer)
    			.removeCards(game.getPlayerList().get(activePlayer).play(getSelected()));}
    			
    		if (game.getPlayerList().get(activePlayer).getCardsInHand().isEmpty()){
    			printMsg("Player "+activePlayer+" wins the game.");
        		        	
    		for (int i=0; i<4; i++){
    			printMsg("Player "+i+" has "+game.getPlayerList().get(i).getCardsInHand().size()
        				+" cards in hand.");
    		}
    		BigTwoClient.last=activePlayer;
    		BigTwoClient.lasthand=a;
    		BigTwoClient.handsOnTable.add(a);
        	disable();}
        	else{
        		if (a!=null){
        			BigTwoClient.last=activePlayer;
        			BigTwoClient.lasthand=a;
        			BigTwoClient.handsOnTable.add(a);
    		setActivePlayer((activePlayer+1)%4);}
        	
    		printMsg("Player"+activePlayer+"'s turn");
    		BigTwoClient.currentIdx=activePlayer;
        	}
    		
        	repaint();
       	
    		resetSelected();
    	}
    }
    
    /**
     * PassButton listener to deal with actions
     * when user push the pass button
     * @author CChen
     * @version 1.0
     * @since 2016-11-14
     */
    class PassButtonListener implements ActionListener{
    	
    	/**
    	 * deal with actions when passButton is pushed
    	 */
         public void actionPerformed(ActionEvent event){
        	 resetSelected();
        	 game.makeMove(activePlayer, getSelected());
        	 //game.checkMove(activePlayer,getSelected());
        	 if (!legal){
        		 resetSelected();
        		 repaint();
        		 printMsg("Please choose again!");
        		 printMsg("Player"+activePlayer+"'s turn");
        		 return;
        	 }
    		printMsg("{Pass}");
    		printMsg("");
    		activePlayer=(activePlayer+1)%4;
    		BigTwoClient.currentIdx=activePlayer;
    		printMsg("Player"+activePlayer+"'s turn");
    		repaint();
    		
    	}
    }
    
    /**
     * ConnectMenuItem Listener to deal with actions
     * when user chooses restart button
     * @author CChen
     * @version 1.0
     * @since 2016-11-14
     */
    class ConnectMenuItemListener implements ActionListener{
    	
    	/**
    	 * deal with actions when restart menuItem is pushed
    	 */
    	public void actionPerformed(ActionEvent event){
        	for (int i=0; i<4; i++)
        	game.getPlayerList().get(i).removeAllCards();
        	BigTwoClient.lasthand=null;
            isFirst=true;
            if (game.getPlayerList().size()==4){
            	printMsg("The game is already full");
            }
            game.makeConnection();
            inputDialog();
    	}
    	
    }
    
    /**
     * QuitMenuItem Listener to deal with actions
     * when user chooses quit button
     * @author CChen
     * @version 1.0
     * @since 2016-11-14
     */
    class QuitMenuItemListener implements ActionListener{
    	
    	/**
    	 * deal with actions when quit menuItem is pushed
    	 */
    	public void actionPerformed(ActionEvent event){
    		 System.exit(0);
    	}
    }
    
    /**
     * print the information about Hand a 
     * in the msgArea
     * @param printFront
     * @param printIndex
     */
    public void print(boolean printFront, boolean printIndex) {
    	
    	if (a==null) return;
    	if (a.size() > 0) {
    		for (int i = 0; i < a.size(); i++) {
    			String string = "";
    			if (printIndex) {
    				string = i + " ";
    			}
    			if (printFront) {
    				string = string + "[" + a.getCard(i) + "]";
    			} else {
    				string = string + "[  ]";
    			}
    			if (i % 13 != 0) {
    				string = " " + string;
    			}
    			printMsg(string);
    			if (i % 13 == 12 || i == a.size() - 1) {
    				printMsg("");
    			}
    		}
    	} else {
    		printMsg("[Empty]");
    	}
    }

    /**
     * a simplified version of print(boolean,boolean)
     */
    public void print() {
    	print(true, false);
    }
}
 