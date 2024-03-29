// Whist.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("serial")
public class Whist extends CardGame {
	
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    // Reverse order of rank importance (see rankGreater() below)
	// Order of cards is tied to card images
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }
  
  final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

  static final Random random = ThreadLocalRandom.current();
  
  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }
  
  // return random Card from Hand
  public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }
 
  // return random Card from ArrayList
  public static Card randomCard(ArrayList<Card> list){
      int x = random.nextInt(list.size());
      return list.get(x);
  }
  
  public static boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }
	 
  private int n_interactive;
  private int n_random_npcs;
  private int n_legal_npcs;
  private int n_smart_npcs;
  private final String version = "1.0";
  public int nbPlayers;
  public int nbStartCards;
  public int winningScore;
  private final int handWidth = 400;
  private final int trickWidth = 40;
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  ArrayList<IPlayerAdapter> players = new ArrayList<IPlayerAdapter>();
  private final Location[] handLocations = {
			  new Location(350, 625),
			  new Location(75, 350),
			  new Location(350, 75),
			  new Location(625, 350)
	  };
  private final Location[] scoreLocations = {
			  new Location(575, 675),
			  new Location(25, 575),
			  new Location(575, 25),
			  new Location(650, 575)
	  };
  private Actor[] scoreActors = {null, null, null, null };
  private final Location trickLocation = new Location(350, 350);
  private final Location textLocation = new Location(350, 450);
  private final int thinkingTime = 2000;
  private Hand[] hands;
  private Location hideLocation = new Location(-500, - 500);
  private Location trumpsActorLocation = new Location(50, 50);
  private boolean enforceRules=false;

  public void setStatus(String string) { setStatusText(string); }
  
  private int[] scores;

  Font bigFont = new Font("Serif", Font.BOLD, 36);

  private void initScore() {
	 scores = new int[nbPlayers];
	 for (int i = 0; i < nbPlayers; i++) {
		 scores[i] = 0;
		 scoreActors[i] = new TextActor("0", Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

  private void updateScore(int player) {
	removeActor(scoreActors[player]);
	scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
  }

  private Card selected;

  private void initRound() {
		 hands = deck.dealingOut(nbPlayers, nbStartCards); // Last element of hands is leftover cards; these are ignored
		 for (int i = 0; i < nbPlayers; i++) {
			   hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		 }
		 // Set up human player for interaction
		CardListener cardListener = new CardAdapter()  // Human Player plays card
			    {
			      public void leftDoubleClicked(Card card) { selected = card; hands[0].setTouchEnabled(false); }
			    };
		hands[0].addCardListener(cardListener);
		 // graphics
	    RowLayout[] layouts = new RowLayout[nbPlayers];
	    for (int i = 0; i < nbPlayers; i++) {
	      layouts[i] = new RowLayout(handLocations[i], handWidth);
	      layouts[i].setRotationAngle(90 * i);
	      // layouts[i].setStepDelay(10);
	      hands[i].setView(this, layouts[i]);
	      hands[i].setTargetArea(new TargetArea(trickLocation));
	      hands[i].draw();
	    }
	    //for (int i = 1; i < nbPlayers; i++)  // This code can be used to visually hide the cards in a hand (make them face down)
           //  hands[i].setVerso(true);
	    // End graphics
  }

  private Optional<Integer> playRound() {  // Returns winner, if any
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, trumpsActorLocation);
	// End trump suit
	Hand trick;
	int winner;
	Card winningCard;
	Suit lead;
	int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
	for (int i = 0; i < nbStartCards; i++) {
		trick = new Hand(deck);
    	selected = null;
        if (players.get(nextPlayer).getType().equals("Interactive_Players")) {  // Select lead depending on player type
        	hands[nextPlayer].setTouchEnabled(true);
    		setStatusText("Player 0 double-click on card to lead.");
    		while (null == selected) delay(100);
        } else {
    		setStatusText("Player " + nextPlayer + " thinking...");
            delay(thinkingTime);
            selected = randomCard(hands[nextPlayer]);
        }
        
        // Lead with selected card
	        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
		// End Lead
		for (int j = 1; j < nbPlayers; j++) {
			if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
			selected = null;
	        if (players.get(nextPlayer).getType().equals("Interactive_Players")) {
	        	hands[nextPlayer].setTouchEnabled(true);
	    		setStatusText("Player 0 double-click on card to lead.");
	    		while (null == selected) delay(100);
	        } else {
		        setStatusText("Player " + nextPlayer + " thinking...");
		        delay(thinkingTime);
		        selected = players.get(nextPlayer).selectCard(trumps, lead, hands[nextPlayer]);
	        }
	        // Follow with selected card
		        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
					if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
						 // Rule violation
						 String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
						 //System.out.println(violation);
						 if (enforceRules) 
							 try {
								 throw(new BrokeRuleException(violation));
								} catch (BrokeRuleException e) {
									e.printStackTrace();
									System.out.println("A cheating player spoiled the game!");
									System.exit(0);
								}  
					 }
				// End Check

				 selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				 System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + winningCard.getRankId());
				 System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " +    selected.getRankId());
				 if ( // beat current winner with higher card
					 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
					  // trumped when non-trump was winning
					 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					 //System.out.println("NEW WINNER");
					 winner = nextPlayer;
					 winningCard = selected;
				 }
			// End Follow
		}
		delay(600);
		trick.setView(this, new RowLayout(hideLocation, 0));
		trick.draw();		
		nextPlayer = winner;
		setStatusText("Player " + nextPlayer + " wins trick.");
		scores[nextPlayer]++;
		updateScore(nextPlayer);
		if (winningScore == scores[nextPlayer]) return Optional.of(nextPlayer);
	}
	removeActor(trumpsActor);
	return Optional.empty();
  }
  
  public void propertiesReader() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
	  
	  Properties whistProperties = new Properties();
	  Properties parametersProperties = new Properties();
	  
	  // Read properties
	  FileReader inStream = null;
	  try {
		  inStream = new FileReader("whist/whist.properties");
		  whistProperties.load(inStream);
	  } finally {
		  if (inStream != null) {
			  inStream.close();
	      }
	  }
	  
	  // Read properties
	  try {
		  inStream = new FileReader("whist/parameters.properties");
		  parametersProperties.load(inStream);
	  } finally {
		  if (inStream != null) {
			  inStream.close();
		  }
	  }
	  
	  // numbers of players
	  nbPlayers = Integer.parseInt(parametersProperties.getProperty("NbPlayers"));
	  // numbers of start cards
	  nbStartCards = Integer.parseInt(parametersProperties.getProperty("NbStartCards"));
	  // winning score
	  winningScore = Integer.parseInt(parametersProperties.getProperty("WinningScore"));
      
	  // numbers of interactive players
	  n_interactive = Integer.parseInt(whistProperties.getProperty("Interactive_Players"));
	  // numbers of random NPCs
	  n_random_npcs = Integer.parseInt(whistProperties.getProperty("Random_NPCs"));
	  // numbers of legal NPCs
	  n_legal_npcs = Integer.parseInt(whistProperties.getProperty("Legal_NPCs"));
	  // numbers of smart NPCs
	  n_smart_npcs = Integer.parseInt(whistProperties.getProperty("Smart_NPCs"));
	  
	  // position 0
	  String position0 = whistProperties.getProperty("Position0");
	  System.out.println(position0);
	  // position1
	  String position1 = whistProperties.getProperty("Position1");
	  System.out.println(position1);
	  // position2
	  String position2 = whistProperties.getProperty("Position2");
	  System.out.println(position2);
	  // position3
	  String position3 = whistProperties.getProperty("Position3");
	  System.out.println(position3);

	  players.add(PlayerFactory.getInstance().getNPCAdapter(position0));
	  players.add(PlayerFactory.getInstance().getNPCAdapter(position1));
      players.add(PlayerFactory.getInstance().getNPCAdapter(position2));
	  players.add(PlayerFactory.getInstance().getNPCAdapter(position3));
	  
  }

  public Whist() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException
  {
    super(700, 700, 30);
    setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");
    propertiesReader();
    initScore();
    Optional<Integer> winner;  
    do { 
      initRound();
      winner = playRound();
    } while (!winner.isPresent());
    addActor(new Actor("sprites/gameover.gif"), textLocation);
    setStatusText("Game over. Winner is player: " + winner.get());
    refresh();
  }

  public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException
  {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
    new Whist();
  }

}
