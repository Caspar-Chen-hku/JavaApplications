/**
 * <h1>This is the Straight class!</h1>
 * A subclass of hand
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class Straight extends Hand{
	/**
	 * public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public Straight(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * overridden function
	 */
	public boolean beats(Hand hand){
		//if (hand==null) return false;
 		if (hand.getType()=="Straight" && 
				(getTopCard().compareTo(hand.getTopCard())==1)){
			return true;
		}
		else 
			return false;
	}
	/**
	 * overridden function to get type
	 */
	public String getType(){return "Straight";}
	/**
	 * Overridden function to determine whether valid
	 */
	public boolean isValid(){
		/*if (size()==5){
		int[] test = new int[5];
		   for (int i=0; i<5; i++){
			   test[i]=getCard(i).getRank();
		   }
		  return consecutive(test);}
		else return false;*/
		return (size()==5&&getCard(4).getRank()-getCard(0).getRank()==4);
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
}
