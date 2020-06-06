import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LegalNPC {

	private Card selected;
	private Card temp;
	private ArrayList<Card> cardList; // this array list contains all cards in hand
	private ArrayList<Card> legalCards = new ArrayList<Card>();
	private Random random = new Random();
	
	public Card legalSelect(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
	    cardList = hand.getCardList();
	    selected = Whist.randomCard(hand);
	 
	    for(int i=0; i < hand.getNumberOfCards(); i++) {
            temp = cardList.get(i);
            if(temp.getSuit() == trumps || temp.getSuit() == lead) {
                legalCards.add(temp);
            }
        }
	    
	    if(!legalCards.isEmpty()) { // hand has legal cards 
            // choose a random legal card
            int x = random.nextInt(legalCards.size());
            selected = legalCards.get(x);
        } 
	    
		return selected;
	}
	
}
