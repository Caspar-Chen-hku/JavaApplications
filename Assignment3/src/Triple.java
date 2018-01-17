/**
 * <h1>This is the Triple class!</h1>
 * A subclass of hand
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class Triple extends Hand{
	/**
	 * public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public Triple(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * Overridden function
	 */
	public boolean beats(Hand hand){
		if (hand==null)  return false;
		if (hand.getType()=="Triple" && 
				getCard(0).compareTo(hand.getCard(0))==1){
			return true;
		}
		else 
			return false;
	}
	/**
	 * Overridden function to get type
	 */
	public String getType(){return "Triple";}
	/**
	 * Overridden function to determine whether valid
	 */
	public boolean isValid(){
		if (size()==3)
		return (getCard(0).getRank()==getCard(1).getRank()&&
				getCard(0).getRank()==getCard(2).getRank());
		else return false;
	}
}
