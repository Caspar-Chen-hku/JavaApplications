/**
 * <h1>This is the Quad class!</h1>
 * a subclass of hand
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class Quad extends Hand{
	/**
	 * Public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public Quad(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * overridden function
	 */
	public boolean beats(Hand hand){
		//if (hand==null) return false;
		if (hand.getType()=="Quad" && 
				getTopCard().compareTo(hand.getTopCard())==1){
			return true;
		}
		else if (hand.getType()=="Straight"||hand.getType()=="Flush"
				||hand.getType()=="FullHouse")
			return true;
		else 
			return false;
	}
	/**
	 * overridden function to get type
	 */
	public String getType(){return "Quad";}
	/**
	 * Overridden function to determine whether valid
	 */
	public boolean isValid(){
		if (size()==5){
		 int[] test = new int[5];
		   for (int i=0; i<5; i++){
			   test[i]=getCard(i).getRank();
		   }
			   int i= theDifferent(test);
			   return isQuad(test,i);}
		else return false;
	   
	}
	private boolean isQuad(int[] test, int index){
		int[] newTest = new int[4];
		int counter=0;
		for (int i=0; i<5; i++){
			if (i==index) continue;
			newTest[counter]=test[i];
			counter++;
		}
		int a=newTest[0];
		if (newTest[1]==a&&newTest[2]==a&&newTest[3]==a)
			return true;
		else return false;
	}
	private int theDifferent(int[] test){
		/*for (int i=0; i<5; i++){
			if (find(test,i)==-1)
				return i+1;
		}*/
		if (test[0]!=test[1]) return 0;
		else return 4;
		//return -1;
	}
	private int find(int[] test, int index){
		if (index+1>=5||index<0) return -1;
		for (int i=index+1; i<5; i++){
			//System.out.println(index+" "+i);
			if (i>=5) return -1;
			else if (test[i]==test[index])
				return index;
		}
		return -1;
	}
}
