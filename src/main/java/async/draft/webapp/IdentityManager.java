package async.draft.webapp;

public class IdentityManager {
		
	private static IdentityManager instance = new IdentityManager();
	
	private IdentityList identities;
	
	private IdentityManager(){
		IdentitiesLoader loader = new NetrunnerDBAPILoader();
		identities = loader.retrieveIdentities();
	}
	
	public static IdentityManager getInstance(){
		return instance;
	}
	
	public IdentityList getAllIds(){
		return identities;
	}
	
	public static void main(String args[]){
		IdentityManager.getInstance().getAllIds();
	}
		
}
