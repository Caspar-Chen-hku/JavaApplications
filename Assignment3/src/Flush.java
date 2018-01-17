/**
 * <h1>This is the Flush class!</h1>
 * a SUBCLASS of Hand
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class Flush extends Hand{
	/**
	 * Public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public Flush(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * overriden function
	 */
	public boolean beats(Hand hand){
		//if (hand==null) return false;
		if (hand.getType()=="Flush"&&getTopCard().getSuit()>
		hand.getTopCard().getSuit()){
			return true;
		}
		
		else if (hand.getType()=="Flush" && 
				(getTopCard().compareTo(hand.getTopCard())==1)){
			return true;
		}
		else if  (hand.getType()=="Straight")
			return true;
		else 
			return false;
	}
	/**
	 * overriden function to get type
	 */
	public String getType(){return "Flush";}
	/**
	 * overriden function to determine whether this is valid
	 */
	public boolean isValid(){
		return (size()==5&&sameSuit());
	}
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
