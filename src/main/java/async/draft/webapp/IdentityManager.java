package async.draft.webapp;

public class IdentityManager {
		
	private static IdentityManager instance = new IdentityManager();
	
	private IdentityMap identities;
	
	private IdentityManager(){
		IdentitiesLoader loader = new LocalLoader();
		identities = loader.retrieveIdentities();
	}
	
	public static IdentityManager getInstance(){
		return instance;
	}
	
	public IdentityMap getAllIds(){
		return identities;
	}
	
	public static void main(String args[]){
		IdentityManager.getInstance().getAllIds();
	}
	
	public Identity getIdentity(String code){
		return identities.get(code);
	}
		
}
