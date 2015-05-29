package async.draft.webapp;

import java.util.ArrayList;
import java.util.List;

public class IdentityList extends ArrayList<Identity> {

	private static final long serialVersionUID = 7876281237162379911L;
	
	public List<Identity> getAllSideIdentities(int side){
		List<Identity> subset = new ArrayList<Identity>();
		for (int i=0; i<this.size();i++){
			if (this.get(i).getSide() == side){
				subset.add(this.get(i));
			}
		}
		return subset;
	}
}
