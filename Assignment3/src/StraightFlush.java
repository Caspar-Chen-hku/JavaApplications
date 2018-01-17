/**
 * <h1>This is the StrightFlush class!</h1>
 * a subclass of Hand
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class StraightFlush extends Hand{
	/**
	 * public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public StraightFlush(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * overridden function
	 */
	public boolean beats(Hand hand){
		//if (hand==null) return false;
		if (hand.getType()=="StraightFlush" && 
				(getTopCard().compareTo(hand.getTopCard())==1)){
			return true;
		}
		else if (hand.getType()=="Straight"||hand.getType()=="Flush"
			||hand.getType()=="FullHouse" || hand.getType()=="Quad")
			return true;
		else 
			return false;
	}
	/**
	 * Overridden function to get type
	 */
	public String getType(){return "StraightFlush";}
	/**
	 * Overridden function to determine whether valid
	 */
	public boolean isValid(){
		if (size()==5){
		 int[] test = new int[5];
		   for (int i=0; i<5; i++){
			   test[i]=getCard(i).getRank();
		   }
		   return (test[4]-test[0]==4 && sameSuit( ));}
		else return false;
		  
	}
	/*private boolean consecutive(int[] test){
		int high=4;
		int low=0;
		for (int i=0; i<5; i++){
			if (test[i]>high) high = test[i];
			else if (test[i]<low) low = test[i];
		}
		if (high-low==4) return true;
		else return false;
	}*/
	private boolean sameSuit(){
		for (int i=1; i<5; i++){
			//if (i>=size()) return false;
			if (getCard(i).getSuit()!=
					getCard(i-1).getSuit())
				return false;
		}
		return true;
	}
}
