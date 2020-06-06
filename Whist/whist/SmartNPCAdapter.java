
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SmartNPCAdapter implements IPlayerAdapter {
	
	private String type;
	
	public SmartNPCAdapter(String type) {
		this.type = type;
	}
	
	private SmartNPC smartNPC = new SmartNPC();
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		return smartNPC.smartSelect(trumps, lead, hand);
	}
	
	public String getType() {
		return this.type;
	}
}
