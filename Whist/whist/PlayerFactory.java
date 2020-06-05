public class PlayerFactory {

	private IPlayerAdapter NPCAdapter;
	private static PlayerFactory instance;
	
	// Factory
	public IPlayerAdapter getNPCAdapter(String NPCtype) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (NPCAdapter == null) {
			if (NPCtype.equals("Legal_NPCs")) {
				NPCAdapter = (IPlayerAdapter) new LegalNPCAdapter(NPCtype);
			} else if (NPCtype.equals("Random_NPCs")) {
				NPCAdapter = (IPlayerAdapter) new RandomNPCAdapter(NPCtype);
			} else if (NPCtype.contentEquals("Smart_NPCs")){
				NPCAdapter = (IPlayerAdapter) new SmartNPCAdapter(NPCtype);
			} else {
				NPCAdapter = (IPlayerAdapter) new InteractiveAdapter(NPCtype);
			}
		}
		return NPCAdapter;
	}
	
	// Singleton
	public static synchronized PlayerFactory getInstance() {
		if (instance == null) {
			instance = new PlayerFactory();
		}
		return instance;
	}
}
