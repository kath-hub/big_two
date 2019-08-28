/**
 * The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a
Big Two card game. It should override the compareTo() method it inherited from the Card
class to reflect the ordering of cards used in a Big Two card game.
 * @author kat
 *
 */
public class BigTwoCard extends Card{
	
	/**
	 * a constructor for building a card with the specified
suit and rank
	 * @param suit is an integer between 0 and
	 * @param rank is an integer between 0 and 12.
	 */
	
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
		
	}
	
	/**
	 * a method for comparing the order of this card with the
specified card. Returns a negative integer, zero, or a positive integer as this card is less
than, equal to, or greater than the specified card.
@param card the
specified card
	 */
	public int compareTo(Card card) {
		if (this.rank >= 2 && card.rank >=2 ) {
			 return super.compareTo(card);
		 } else {
			 if (this.rank == 1 && card.rank != 1) {
				 return 1;
			 } else if (this.rank != 1 && card.rank == 1) {
				 return -1;
			 } else if (this.rank == 1 && card.rank == 1 ) {
				 return super.compareTo(card);
			 } else {
			 
				 if (this.rank > card.rank) {
					 return -1;
				 } else if (this.rank < card.rank) {
					 return 1;
				 } else {
					 return super.compareTo(card);
				 }
			 }
			 
		 }
	}
		 	 

	
}
