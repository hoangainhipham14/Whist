
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class InteractiveAdapter implements IPlayerAdapter{

	private String type;
	
	public InteractiveAdapter(String type) {
		this.type = type;
	}

	private InteractivePlayer interactivePlayer = new InteractivePlayer();
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hand, Card winningCard) {
		return Whist.randomCard(hand);
	}
	
	public String getType() {
		return this.type;
	}
}
