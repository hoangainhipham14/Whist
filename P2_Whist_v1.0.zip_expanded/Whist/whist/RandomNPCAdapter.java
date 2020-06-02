
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNPCAdapter implements INPCAdapter {
	
	private RandomNPC randomNPC;
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand, Card winningCard) {
		return randomNPC.randomSelect(trumps, lead, hand);
	}
}
