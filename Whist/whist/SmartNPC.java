import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SmartNPC {

	private Card selected;
	private Card temp;
	private ArrayList<Card> cardList;
	
	public Card smartSelect(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
	
		selected = Whist.randomCard(hand);
		
		cardList = hand.getCardList();
	    selected = cardList.get(0); // set selected to first card in hand
		
	    for(int i=0; i < hand.getNumberOfCards(); i++) {
            temp = cardList.get(i);
            if(temp.getSuit() == trumps) {
                if(Whist.rankGreater(temp, selected)) {
                    selected = temp;
                }
            } else if(temp.getSuit() == lead) {
                if(Whist.rankGreater(temp, selected)) {
                    selected = temp;
                }
            } 
        }
        return selected;
	}
}
