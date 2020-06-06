
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LegalNPCAdapter implements IPlayerAdapter{
	
	private String type;
	
	public LegalNPCAdapter(String type) {
		this.type = type;
	}

	private LegalNPC legalNPC = new LegalNPC();
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		return legalNPC.legalSelect(trumps, lead, hand);
	}
	
	public String getType() {
		return this.type;
	}
}

