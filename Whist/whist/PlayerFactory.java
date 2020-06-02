
// Plz rename class name later!!!!!!
public class PlayerFactory {

	private INPCAdapter NPCAdapter;
	private static PlayerFactory instance;
	
	// Factory
	public INPCAdapter getNPCAdapter(INPCAdapter npc) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (NPCAdapter == null) {
			String className = System.getProperty("npc.class.name");
			NPCAdapter = (INPCAdapter) Class.forName(className).newInstance();
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
