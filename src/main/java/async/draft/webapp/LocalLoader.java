package async.draft.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class LocalLoader implements IdentitiesLoader {

	@Override
	public IdentityList retrieveIdentities() {
		IdentityList identities = new IdentityList();

		try {
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("identitiesLocal.json");

			JsonNode rootNode = new ObjectMapper().readTree(in);

			Iterator<JsonNode> cardsIterator = rootNode.getElements();

			while (cardsIterator.hasNext()) {
				JsonNode node = cardsIterator.next();

				Identity identity = new Identity();
				identity.setName(node.get("name").getTextValue());
				identity.setSide(node.get("side").getIntValue());
				identity.setImgsrc(node.get("imgsrc").getTextValue());
				identities.add(identity);
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return identities;
	}

}
