/**
 * his hand consists of five cards with consecutive ranks and the same
suit. For
 * @author kat
 *
 */
public class StraightFlush extends Hand {
	/**
	 * constructor for building a StraightFlush with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	public StraightFlush (CardGamePlayer player, CardList cards){
		super(player, cards);
	};
	
	/**
	 * a method for checking if this is a valid StraightFlush
	 * @return a boolean value if this is a valid StraightFlush
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
						return ((c2.getRank()== 3 && c3.getRank()== 4 && c4.getRank()==5 && c5.getRank()==6));
					} else if (c1.getRank() == 3) {
						return ((c2.getRank()== 4 && c3.getRank()== 5 && c4.getRank()== 6 && c5.getRank()== 7));
					} else if (c1.getRank() == 4) {
						return ((c2.getRank()== 5 && c3.getRank()== 6 && c4.getRank()== 7 && c5.getRank()== 8));
					} else if (c1.getRank() == 5) {
						return ((c2.getRank()== 6 && c3.getRank()== 7 && c4.getRank()== 8 && c5.getRank()==9));
					} else if (c1.getRank() == 6) {
						return ((c2.getRank()== 7 && c3.getRank()== 8 && c4.getRank()== 9 && c5.getRank()==10));
					} else if (c1.getRank() == 7) {
						return ((c2.getRank()== 8 && c3.getRank()== 9 && c4.getRank()==10 && c5.getRank()==11));
					} else if (c1.getRank() == 8) {
						return ((c2.getRank()== 9 && c3.getRank()== 10 && c4.getRank()==11 && c5.getRank()==12));
					} else if (c1.getRank() == 9) {
						return ((c2.getRank()== 10 && c3.getRank()== 11 && c4.getRank()==12 && c5.getRank()==0));
					} else if (c1.getRank() == 10) {
						return ((c2.getRank()== 11 && c3.getRank()== 12 && c4.getRank()==0 && c5.getRank()==1));
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
		return "StraightFlush";
	}
}
