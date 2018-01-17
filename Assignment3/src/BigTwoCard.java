/**
 * <h1>This is the BigTwoCard class!</h1>
 * The BigTwoCard class is a subclass of the Card class, 
 * and is used to model a card used in a Big Two card game. 
 * It should override the compareTo() method it inherited 
 * from the Card class to reflect the ordering of cards 
 * used in a Big Two card game. 
 * @author CChen
 * @version 1.0
 * @since 2016-10-20
 */



public class BigTwoCard extends Card{
	
/**
 * Public constructor
 * @param suit suit of the card
 * @param rank rank of the card
 */
 public BigTwoCard(int suit, int rank){
    super(suit,rank);
}
 /**
  * overriden function
  */
 public int compareTo(Card card){
	 if (rank==0&&card.getRank()!=1&&card.getRank()!=0)
		 return 1;
	 else if (rank==1&&card.getRank()!=1)
		 return 1;
	 else if (card.getRank()==0&&rank!=0&&rank!=1)
		 return -1;
	 else if (card.getRank()==1&&rank!=1)
		 return -1;
	 else if (rank>card.getRank()){
		 return 1;
	 }
	 else if (rank<card.getRank()){
		 return -1;
	 }
	 else {
		 if (suit>card.getSuit()){
			 return 1;
		 }
		 else if (suit<card.getSuit()){
			 return -1;
		 }
		 else{
			 return 0;
		 }
	 }
 }
 
}
