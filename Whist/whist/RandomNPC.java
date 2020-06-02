import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class RandomNPC {
	
	public Card randomSelect(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		return Whist.randomCard(hand);
	}
	
}
