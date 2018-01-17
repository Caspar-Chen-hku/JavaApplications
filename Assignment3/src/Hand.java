/**
 * <h1>This is the Hand class!</h1>
 * The Hand class is a subclass of the CardList 
 * class, and is used to model a hand of cards. 
 * It has a private instance variable for storing 
 * the player who plays this hand. It also has 
 * methods for getting the player of this hand, 
 * checking if it is a valid hand, getting the 
 * type of this hand, getting the top card of
 * this hand, and checking if it beats a specified hand. 
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */




public abstract class Hand extends CardList{
/**
 * Public constructor
 * @param player CardGamePlayer
 * @param cards CardList
 */
	public Hand(CardGamePlayer player, CardList cards){
		this.player = player;
		for (int i=0; i<cards.size(); i++){
			addCard(cards.getCard(i));
		}
		
	}
   private CardGamePlayer player;
   /**
    * public getter to get player
    * @return player
    */
   public CardGamePlayer getPlayer(){
	   return player;
   }
   //public CardList getCards(){return cards;}
   /**
    * public function to get top card
    * @return top card
    */
   public Card getTopCard() {
	   if (getType()=="Single"){
		   return getCard(0);
	   }
	   else if (getType()=="Pair"){
		   if (getCard(0).suit>getCard(1).suit)
			   return getCard(0);
		   else
			   return getCard(1);
	   }
	   else if (getType()=="Triple"){
		   if (getCard(0).suit>getCard(1).suit &&
				   getCard(0).suit>getCard(2).suit){
			   return getCard(0);
	   }
	   else if (getCard(1).suit>getCard(0).suit &&
			   getCard(1).suit>getCard(2).suit) {
		   return getCard(1);}
	   else
		   return getCard(2);
   }
   else if (getType()=="Straight" || getType()=="Flush" || getType()=="StraightFlush"){
	   int index = 0;
	   for (int i=1; i<5; i++){
		   if (compareTo(getCard(i),getCard(index))==1){
			   index=i;
		   }
	   }
	   return getCard(index);
   }
   else if (getType()=="FullHouse"){
	   int[] test = new int[5];
	   for (int i=0; i<5; i++){
		   test[i]=getCard(i).getRank();
	   }
	   int index1 = find(test,0);
	   int index2 = find(test,index1);
	   if (index2==-1){
		   int high = 0;
		   int index = 0;
		   for (int i=0; i<5; i++){
			   if (i==0 || i==index1)
				   continue;
			   else if (getCard(i).getSuit()>high){
				   high = getCard(i).getSuit();
				   index=i;
			   }
		   }
			return getCard(index);   
		   }
	   else{
		   int high = 0;
		   int index = 0;
		   for (int i=0; i<5; i++){
			   if (i!=0 && i!=index1 && i!=index2)
				   continue;
			   else if (getCard(i).getSuit()>high){
				   high = getCard(i).getSuit();
				   index=i;
			   }
		   }
			return getCard(index);
	   }
	   }
   else if (getType()=="Quad"){
	   int[] test = new int[5];
	   for (int i=0; i<5; i++){
		   test[i]=getCard(i).getRank();
	   }
	   int high = 0;
	   int index = 0;
	   int diff = theDifferent(test);
	   for (int i=0; i<5; i++){
		   if (i==diff)
			   continue;
		   else if (getCard(i).getSuit()>high){
			   high = getCard(i).getSuit();
			   index=i;
	   }
   }
	   return getCard(index);
   }
   else
	   return getCard(0);	    
}
   /**
    * public function to determine whether it can beat
    * it will be overriden
    * @param hand Hand
    * @return boolean
    */
   public abstract boolean beats(Hand hand);
   /**
    * public function to determine whether it is valid
    * it will be overriden
    * @return boolean
    */
   public abstract boolean isValid();
   /**
    * public function to get type
    * it will be overriden
    * @return String
    */
   public abstract String getType();
   
   private int find(int[] test, int index){
		if (index+1>=5||index<0) return -1;
		for (int i=index+1; i<5; i++){
			//System.out.println(index+" "+i);
			if (test[i]==test[index])
				return i;
		}
		return -1;
	}
private int theDifferent(int[] test){
	for (int i=0; i<5; i++){
		if (find(test,i)==-1)
			return i;
	}
	return -1;
}

private int compareTo(Card card1,Card card2){
	 if (card1.getRank()==0&&card2.getRank()!=1&&card2.getRank()!=0)
		 return 1;
	 else if (card1.getRank()==1&&card2.getRank()!=1)
		 return 1;
	 else if (card2.getRank()==0&&card1.getRank()!=0&&card1.getRank()!=1)
		 return -1;
	 else if (card2.getRank()==1&&card1.getRank()!=1)
		 return -1;
	 else if (card1.getRank()>card2.getRank()){
		 return 1;
	 }
	 else if (card1.getRank()<card2.getRank()){
		 return -1;
	 }
	 else {
		 if (card1.getSuit()>card2.getSuit()){
			 return 1;
		 }
		 else if (card1.getSuit()<card2.getSuit()){
			 return -1;
		 }
		 else{
			 return 0;
		 }
	 }
}


}
