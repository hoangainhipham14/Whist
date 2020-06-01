import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class LegalNPC {

	private Hand legalNPC;
	private Card selected;
	
	public LegalNPC(Hand legalNPC) {
		this.legalNPC = legalNPC;
	}
	
	public Card legalSelect(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		
		selected = Whist.randomCard(hand);
		
		while ((selected.getSuit() != trumps && hand.getNumberOfCardsWithSuit(trumps) > 0) || (selected.getSuit() != lead && hand.getNumberOfCardsWithSuit(lead) > 0)) {
			selected = Whist.randomCard(hand);
		}
		
		return selected;
	}
	
}
