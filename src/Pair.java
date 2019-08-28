/**
 * This hand consists of two cards with the same rank. The card with a higher suit
in a pair is referred to as the top card of this pair. A pair with a higher rank beats a
pair with a lower rank. For pairs with the same rank, the one containing the highest
suit beats the other
 * @author kat
 *
 */
public class Pair extends Hand {
	/**
	 * constructor for building a Pair with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	
	public Pair (CardGamePlayer player, CardList cards){
		super(player, cards);
	};
	/**
	 * a method for checking if this is a valid Pair.
	 * @return a boolean value if this is a valid Pair.
	 */
	public boolean isValid() {
		if (super.size() == 2) {
			Card c1 = super.getCard(0);
			Card c2 = super.getCard(1);
			return (c1.getRank()==c2.getRank());
		}
			return false;
	}
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return a string specifying the type of this hand
	 */
	
	public String getType() {
		return "Pair";
	}
	
}
