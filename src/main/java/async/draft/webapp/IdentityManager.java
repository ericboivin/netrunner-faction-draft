package async.draft.webapp;

public class IdentityManager {
		
	private static IdentityManager instance = new IdentityManager();
	
	private IdentityList identities;
	
	private IdentityManager(){
		IdentitiesLoader loader = new LocalLoader();
		identities = loader.retrieveIdentities();
	}
	
	public static IdentityManager getInstance(){
		return instance;
	}
	
	public static void main(String[] args) {
		
		IdentityManager mgr = IdentityManager.getInstance();
		System.out.println(mgr.identities.getAllSideIdentities(Identity.SIDE_RUNNER));
	}
	
	
}
