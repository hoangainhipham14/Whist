
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNPCAdapter implements IPlayerAdapter {
	
	private String type;
	
	public RandomNPCAdapter(String type) {
		this.type = type;
	}
	private RandomNPC randomNPC = new RandomNPC();
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand) {
		return randomNPC.randomSelect(hand);
	}
	
	public String getType() {
		return this.type;
	}
}
