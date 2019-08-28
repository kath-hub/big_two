/**
 * This hand consists of only one single card. The only card in a single is
referred to as the top card of this single. A single with a higher rank beats a single
with a lower rank. For singles with the same rank, the one with a higher suit beats the
one with a lower suit.
 * @author kat
 *
 */
public class Single extends Hand {
	/**
	 * constructor for building a Single with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	public Single(CardGamePlayer player, CardList cards){
		super(player, cards);
	};
	
	/**
	 * a method for checking if this is a valid Single.
	 * @return a boolean value if this is a valid Single.
	 */
	
	public boolean isValid() {
		if (super.size() == 1) {
			return true;
		}
			return false;
	}
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return a string specifying the type of this hand
	 */
	
	public String getType() {
		return "Single";
	}
	
}
