
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class InteractiveAdapter extends CardGame{

	Card selected = null;
	private String type;
	
	public InteractiveAdapter(String type) {
		this.type = type;
	}
	
	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hands, Card winningCard) {
		hands.setTouchEnabled(true);
		setStatusText("Player 0 double-click on card to lead.");
		while (null == selected) delay(100);
		return selected;
	}
	
	public String getType() {
		return this.type;
	}
}
