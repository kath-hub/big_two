/**
 * The Hand class is a subclass of the CardList class, and is used to model a hand of cards. It
has a private instance variable for storing the player who plays this hand. It also has methods
for getting the player of this hand, checking if it is a valid hand, getting the type of this hand,
getting the top card of this hand, and checking if it beats a specified hand.
 * @author kat
 *
 */
public abstract class Hand extends CardList {
	/**
	 * the player who plays this hand.
	 */
	private CardGamePlayer player;
	
	/**
	 * constructor for building a hand
with the specified player and list of cards.
	 * @param player
	 * @param cards
	 */
	public Hand (CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i = 0; i < cards.size(); ++i ) {
			this.addCard(cards.getCard(i));
			
		}
	}
	
	/**
	 * a method for retrieving the player of this hand.
	 * @return the player of this hand
	 */
	
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**
	 * a method for retrieving the top card of this hand.
	 * @return the top card of this hand
	 */
	public Card getTopCard() {
		this.sort();
		return this.getCard(this.size()-1);
	}
	
	/**
	 * a method for checking if this hand beats a specified hand
	 * @param hand
	 * @return a boolean value if if this hand beats a specified hand
	 */
	
	public boolean beats(Hand hand) {
		if (hand.isEmpty()) {
			return true;
		}
		
		String thistype = this.getType();
		String handtype = hand.getType();
		
		if (this.isValid() && hand.isValid()) {
			if (this.size()==hand.size()) {
				if (thistype==handtype) {
					 return (this.getTopCard().compareTo(hand.getTopCard()) > 0);
				} else {
					if (thistype == "Straight") {
						return false;
					} else if (thistype == "StraightFlush"){
						return true;
					} else if (thistype == "Flush") {
						return (handtype == "Straight");
					} else if (thistype == "FullHouse") {
						return (handtype == "Straight" || handtype == "Flush");
					} else if (thistype == "Quad") {
						return (handtype == "Straight" || handtype == "Flush" || handtype == "FullHouse");
					}
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * a method for checking if this is a valid hand.
	 * @return a boolean value if this is a valid hand.
	 */
	public abstract boolean isValid();
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return a string specifying the type of this hand
	 */
	public abstract String getType();
}
