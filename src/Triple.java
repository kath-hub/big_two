/**
 * This hand consists of three cards with the same rank. The card with the
highest suit in a triple is referred to as the top card of this triple. A triple with a higher
rank beats a triple with a lower rank.
 * @author kat
 *
 */
public class Triple extends Hand{
	/**
	 * constructor for building a Triple with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	public Triple (CardGamePlayer player, CardList cards){
		super(player, cards);
	};
	
	/**
	 * a method for checking if this is a valid Triple.
	 * @return a boolean value if this is a valid Triple.
	 */
	
	public boolean isValid() {
		if (super.size() == 3) {
			Card c1 = super.getCard(0);
			Card c2 = super.getCard(1);
			Card c3 = super.getCard(2);
			return (c1.getRank()==c2.getRank() && c1.getRank() == c3.getRank());
		}
			return false;
	}
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return a string specifying the type of this hand
	 */
	
	public String getType() {
		return "Triple";
	}
}
