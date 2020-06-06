import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class RandomNPC {
	
	public Card randomSelect(Hand hand) {
		return Whist.randomCard(hand);
	}
	
}
