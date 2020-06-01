
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LegalNPCAdapter implements INPCAdapter{

	private LegalNPC legalNPC;
	private Card selected;
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		selected = legalNPC.legalSelect(trumps, lead, hand);
		return selected;
	}
}
