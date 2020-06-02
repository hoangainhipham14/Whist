
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SmartNPCAdapter implements INPCAdapter {
	
	private SmartNPC smartNPC;
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand, Card winningCard) {
		return smartNPC.smartSelect(trumps, lead, hand, winningCard);
	}
}
