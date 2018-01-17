/**
 * <h1>This is the Pair class!</h1>
 * A subclass of hand
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class Pair extends Hand{
	/**
	 * public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public Pair(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * overriden function
	 */
	public boolean beats(Hand hand){
		if (hand==null) return false;
		if (hand.getType()=="Pair" && 
				getCard(0).compareTo(hand.getCard(0))==1){
			return true;
		}
		else 
			return false;
	}
	/**
	 * Overriden function to get type
	 */
	public String getType(){return "Pair";}
	/**
	 * Overriden function to determine whether valid
	 */
	public boolean isValid(){
		if(size()==2&&getCard(0).getRank()==getCard(1).getRank()){
			return true;
		}
		return false;
	}

}
