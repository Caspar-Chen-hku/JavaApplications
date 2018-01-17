/**
 * <h1>This is the FullHouse class!</h1>
 * a subclass of Hand
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class FullHouse extends Hand{
	/**
	 * public constructor
	 * @param player CardGamePlayer
	 * @param cards CardList
	 */
	public FullHouse(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * overriden function
	 */
	public boolean beats(Hand hand){
		//if (hand==null) return false;
		if (hand.getType()=="FullHouse" && 
				getTopCard().compareTo(hand.getTopCard())==1){
			return true;
		}
		else if(hand.getType()=="Straight"||hand.getType()=="Flush")
		return true;
		else
			return false;
	}
	/**
	 * overriden function to get type
	 */
	public String getType(){return "FullHouse";}
	/**
	 * overriden function to determine whether this is valid
	 */
	public boolean isValid(){
		if (size()==5){
			 int[] test = new int[5];
			   for (int i=0; i<5; i++){
				   test[i]=getCard(i).getRank();
			   }
			   int index1 = find(test,0);
			   int index2 = find(test,index1);
			   if (index1==-1) return false;
			   else if (index2==-1){
				  /* int high = 0;
				   int index = 0;
				   for (int i=0; i<5; i++){
					   if (i==0 || i==index1)
						   continue;
					   else if (getCard(i).getSuit()>high){
						   high = getCard(i).getSuit();
						   index=i;
					   }
				   }
					return (index>=0&&index<5);   */
				   for (int i=1; i<5; i++){
					   if (i!=index1){
						   index1=i;
						   break;
					   } 
				   }
				   index2 = find(test,index1);
				   int index3 = find (test,index2);
				   return (index3>=0);
				   }
			   else{
				  /* int high = 0;
				   int index = 0;
				   for (int i=0; i<5; i++){
					   if (i!=0 && i!=index1 && i!=index2)
						   continue;
					   else if (getCard(i).getSuit()>high){
						   high = getCard(i).getSuit();
						   index=i;
					   }
				   }
					return (index>=0&&index<5);  */
				   for (int i=1; i<5; i++){
					   if (i!=index1&&i!=index2){
						   index1=i;
						   break;
					   }
				   }
				   index2=find(test,index1);
				   return (index2>=0);
			   }
		}
		return false;
	}
	private int find(int[] test, int index){
		if (index+1>=5||index<0) return -1;
		for (int i=index+1; i<5; i++){
			//System.out.println(index+" "+i);
			if (test[i]==test[index])
				return i;
		}
		return -1;
	}
}
