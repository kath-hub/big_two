/**
 * This hand consists of five cards with the same suit. The card with the highest
rank in a flush is referred to as the top card of this flush. A flush always beats any
straights. A flush with a higher suit beats a flush with a lower suit. For flushes with
the same suit, the one having a top card with a higher rank beats the one having a top
card with a lower rank.
 * @author kat
 *
 */
public class Flush extends Hand {
	
	/**
	 * constructor for building a Flush with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	public Flush (CardGamePlayer player, CardList cards){
		super(player, cards);
	};
	
	/**
	 * a method for checking if this is a valid Flush
	 * @return a boolean value if this is a valid Flush
	 */
	public boolean isValid() {
		if (size() == 5) {
			this.sort();
			Card c1 = getCard(0);
			Card c2 = getCard(1);
			Card c3 = getCard(2);
			Card c4 = getCard(3);
			Card c5 = getCard(4);
			
			if (c1.getSuit()==c2.getSuit() && c1.getSuit()==c3.getSuit() && c1.getSuit()==c4.getSuit() && c1.getSuit()==c5.getSuit()) {
				if (c1.getRank() == 2) {
					return ((c2.getRank()== 3 && c3.getRank()== 4 && c4.getRank()==5 && c5.getRank()==6) == false);
				} else if (c1.getRank() == 3) {
					return ((c2.getRank()== 4 && c3.getRank()== 5 && c4.getRank()== 6 && c5.getRank()== 7) == false);
				} else if (c1.getRank() == 4) {
					return ((c2.getRank()== 5 && c3.getRank()== 6 && c4.getRank()== 7 && c5.getRank()== 8) == false);
				} else if (c1.getRank() == 5) {
					return ((c2.getRank()== 6 && c3.getRank()== 7 && c4.getRank()== 8 && c5.getRank()==9) == false);
				} else if (c1.getRank() == 6) {
					return ((c2.getRank()== 7 && c3.getRank()== 8 && c4.getRank()== 9 && c5.getRank()==10) == false);
				} else if (c1.getRank() == 7) {
					return ((c2.getRank()== 8 && c3.getRank()== 9 && c4.getRank()==10 && c5.getRank()==11) == false);
				} else if (c1.getRank() == 8) {
					return ((c2.getRank()== 9 && c3.getRank()== 10 && c4.getRank()==11 && c5.getRank()==12) == false);
				} else if (c1.getRank() == 9) {
					return ((c2.getRank()== 10 && c3.getRank()== 11 && c4.getRank()==12 && c5.getRank()==0) == false);
				} else if (c1.getRank() == 10) {
					return ((c2.getRank()== 11 && c3.getRank()== 12 && c4.getRank()==0 && c5.getRank()==1) == false );

				} 
			} 
			
		}
			return false;
	}
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return a string specifying the type of this hand
	 */
	
	public String getType() {
		return "Flush";
	}
}
