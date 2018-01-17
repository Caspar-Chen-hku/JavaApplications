/**
 * <h1>This is the BigTwoDeck class!</h1>
 * The BigTwoDeck class is a subclass of the Deck 
 * class, and is used to model a deck of cards used 
 * in a Big Two card game. It should override the 
 * initialize() method it inherited from the Deck
 *  class to create a deck of Big Two cards.
 *  @author CChen
 *  @version 1.0
 *  @since 2016-10-20
 */



public class BigTwoDeck extends Deck {
/**
 * overriden function
 */
  public void initialize(){
	  removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard bigTwoCard = new BigTwoCard(i, j);
				addCard(bigTwoCard);
			}
		}
  }
}
