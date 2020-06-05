
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public interface IPlayerAdapter {

	public Card selectCard(Whist.Suit trumps, Whist.Suit lead, Hand hands, Card winningCard);
	public String getType();
}
