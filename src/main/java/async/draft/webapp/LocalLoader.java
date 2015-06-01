package async.draft.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class LocalLoader implements IdentitiesLoader {

	@Override
	public IdentityMap retrieveIdentities() {
		IdentityMap identities = new IdentityMap();

		try {
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("identitiesLocal.json");

			JsonNode rootNode = new ObjectMapper().readTree(in);

			Iterator<JsonNode> cardsIterator = rootNode.getElements();

			while (cardsIterator.hasNext()) {
				JsonNode node = cardsIterator.next();

				Identity identity = new Identity();
				identity.setName(node.get("name").getTextValue());
				identity.setSide(node.get("side").getTextValue());
				identity.setFaction(node.get("faction").getTextValue());
				identity.setCode(node.get("code").getTextValue());
				identities.put(identity.getCode(),identity);
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return identities;
	}

}
