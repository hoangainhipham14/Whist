import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SmartNPC {

	private Card selected;
	
	public Card smartSelect(Whist.Suit trumps, Whist.Suit lead, Hand hand, Card winningCard) {
		
		selected = Whist.randomCard(hand);
		
		/*while ( // beat current winner with higher card
				 !(selected.getSuit() == winningCard.getSuit() && Whist.rankGreater(selected, winningCard)) ||
				  // trumped when non-trump was winning
				 !(selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
			selected = Whist.randomCard(hand);
		 }*/
		
		return selected;
	}
}
