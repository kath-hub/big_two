/***
 * This hand consists of five cards, with two having the same rank and three
having another same rank. The card in the triplet with the highest suit in a full house
is referred to as the top card of this full house. A full house always beats any straights
and flushes. A full house having a top card with a higher rank beats a full house
having a top card with a lower rank.

 * @author kat
 *
 */
public class FullHouse extends Hand {
	/**
	 * constructor for building a FullHouse with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	private static final long serialVersionUID = 4622772461907486829L;

	public FullHouse (CardGamePlayer player, CardList cards){
		super(player, cards);
	};
	/**
	 * a method for checking if this is a valid FullHouse
	 * @return a boolean value if this is a valid FullHouse
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
				if (c2.getRank()==c3.getRank()) {
					return (c3.getRank()!=c4.getRank() && c4.getRank()==c5.getRank());
				} else {
					return (c3.getRank() == c4.getRank() && c3.getRank() == c5.getRank());
				}
			}
		}
			return false;
			
	}
	
	/**
	 * A overriding method of getTopcard() that return the The card in the triplet with the highest suit in a full house
is referred to as the top card of this full house
	 * @return the top card of this full house
	 */
	
	public Card getTopCard() {
					this.sort();
					//Card c1 = getCard(0);
					Card c2 = getCard(1);
					Card c3 = getCard(2);
					//Card c4 = getCard(3);
					Card c5 = getCard(4);
					
						if (c2.getRank()==c3.getRank()) {
							//if (c3.getRank()!=c4.getRank() && c4.getRank()==c5.getRank()) {
								return c3;
							//}
						} else {
							//if (c3.getRank() == c4.getRank() && c3.getRank() == c5.getRank()) {
								return c5;
							//}
						}
					 
	}
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return a string specifying the type of this hand
	 */
	
	public String getType() {
		return "FullHouse";
	}
}
