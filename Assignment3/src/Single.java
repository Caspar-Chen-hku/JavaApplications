/**
 * <h1>This is the Single class!</h1>
 * A subclass of hand class
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class Single extends Hand{
	/**
	 * public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public Single(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * overridden function
	 */
	
	public boolean beats(Hand hand){
		if (hand==null) return false;
		if (hand.getType()=="Single" && 
				getCard(0).compareTo(hand.getCard(0))==1){
			return true;
		}
		else 
			return false;
	}
	/**
	 * overridden function to get type
	 */
	public String getType(){return "Single";}
	/**
	 * Overridden function to determine whether valid
	 */
	public boolean isValid(){
		
		return (size()==1);}

}
