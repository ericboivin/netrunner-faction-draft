package async.draft.webapp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IdentityMap extends LinkedHashMap<String, Identity> {

	private static final long serialVersionUID = 7876281237162379911L;
	
	public List<Identity> getAllSideIdentities(String side){
		List<Identity> subset = new ArrayList<Identity>();
		for (Map.Entry<String, Identity> entry : this.entrySet())
		{
			if (entry.getValue().getSide().equals(side)){
				subset.add(entry.getValue());
			}
		}
		return subset;
	}

}
