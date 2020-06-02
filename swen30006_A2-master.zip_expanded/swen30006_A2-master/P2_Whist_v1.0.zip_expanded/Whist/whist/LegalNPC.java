import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LegalNPC {

	private Card selected;
	
	public Card legalSelect(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		
		selected = Whist.randomCard(hand);
		
		while ((selected.getSuit() != trumps && hand.getNumberOfCardsWithSuit(trumps) > 0) || (selected.getSuit() != lead && hand.getNumberOfCardsWithSuit(lead) > 0)) {
			selected = Whist.randomCard(hand);
		}
		
		return selected;
	}
	
}
