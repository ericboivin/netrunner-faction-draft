package async.draft.webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class connects on the netrunnerdb API to load all the cards and filter
 * only the identities
 * 
 * @author Eric Boivin
 *
 */
public class NetrunnerDBAPILoader implements IdentitiesLoader {


	public IdentityList retrieveIdentities() {
		
		IdentityList identities = new IdentityList();
		
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"172.206.0.204", 3128));

		try {
			

			URL url = new URL("http://netrunnerdb.com/api/cards/");

			HttpURLConnection uc = (HttpURLConnection) url
					.openConnection(proxy);
			uc.connect();

			JsonNode rootNode = new ObjectMapper()
					.readTree(uc.getInputStream());

			Iterator<JsonNode> cardsIterator = rootNode.getElements();

			while (cardsIterator.hasNext()) {
				JsonNode node = cardsIterator.next();

				if (node.get("type").getTextValue().equals("Identity")) {
					if (node.get("cyclenumber").getIntValue() > 0){
					Identity identity = new Identity();
					identity.setName(node.get("title").getTextValue());
					if (node.get("side").getTextValue().equals("runner")) {
						identity.setSide(Identity.SIDE_RUNNER);
					} else {
						identity.setSide(Identity.SIDE_CORP);
					}
					identity.setImgsrc(node.get("imagesrc").getTextValue());
					identities.add(identity);
					}
				}
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return identities;

	}
}
