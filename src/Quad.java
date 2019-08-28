/**
 * This hand consists of five cards, with four having the same rank. The card in
the quadruplet with the highest suit in a quad is referred to as the top card of this quad.
A quad always beats any straights, flushes and full houses. A quad having a top card
with a higher rank beats a quad having a top card with a lower rank.
 * @author kat
 *
 */
public class Quad extends Hand{
	
	/**
	 * constructor for building a Quad with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	public Quad (CardGamePlayer player, CardList cards){
		super(player, cards);
	};
	
	/**
	 * a method for checking if this is a valid Quad
	 * @return a boolean value if this is a valid Quad
	 */
	
	public boolean isValid() {
		if (size() == 5) {
			this.sort();
			Card c1 = getCard(0);
			Card c2 = getCard(1);
			Card c3 = getCard(2);
			Card c4 = getCard(3);
			Card c5 = getCard(4);
			
			if ( c1.getRank()==c2.getRank()) {
				return (c1.getRank()==c3.getRank() && c1.getRank()==c4.getRank() && c4.getRank()!= c5.getRank());
			} else {
				return (c2.getRank()==c3.getRank() && c2.getRank()==c4.getRank() && c2.getRank()== c5.getRank());
			}
		}
			return false;		
	}
	
	/**
	 * A overriding method of getTopcard() that return the card in the quadruplet with the highest suit in a quad is referred to as the top card of this quad
	 * @return the top card of this quad
	 */
	public Card getTopCard() {
		this.sort();
		Card c1 = getCard(0);
		Card c2 = getCard(1);
		//Card c3 = getCard(2);
		Card c4 = getCard(3);
		Card c5 = getCard(4);
		

			if ( c1.getRank()==c2.getRank()) {
				return c4;
			} else {
				return c5;
			}
}
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return a string specifying the type of this hand
	 */
	
	public String getType() {
		return "Quad";
	}

}
