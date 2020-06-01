
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SmartNPCAdapter implements INPCAdapter {

	private Hand smartNPC;
	private Card selected;
	
	public SmartNPCAdapter(Hand smartNPC) {
		this.smartNPC = smartNPC;
	}
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		return selected;
	}
}
