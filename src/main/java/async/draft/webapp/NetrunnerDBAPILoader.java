package async.draft.webapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
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

	public IdentityMap retrieveIdentities() {

		IdentityMap identities = new IdentityMap();

		try {

			URL url = new URL("http://netrunnerdb.com/api/cards/");

			//For when I'm at work...
			/*
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					"172.206.0.204", 3128));
			HttpURLConnection uc = (HttpURLConnection) url
					.openConnection(proxy);
			*/
			HttpURLConnection uc = (HttpURLConnection) url
					.openConnection();
			
			
			uc.connect();

			JsonNode rootNode = new ObjectMapper()
					.readTree(uc.getInputStream());

			Iterator<JsonNode> cardsIterator = rootNode.getElements();

			while (cardsIterator.hasNext()) {
				JsonNode node = cardsIterator.next();

				if (node.get("type").getTextValue().equals("Identity")) {
					if (node.get("cyclenumber").getIntValue() > 0) {
						Identity identity = new Identity();
						identity.setName(node.get("title").getTextValue());
						identity.setFaction(node.get("faction").getTextValue());
						identity.setCode(node.get("code").getTextValue());
						identity.setSide(node.get("side").getTextValue());
						identities.put(identity.getCode(),identity);
					}
				}
			}
			//Save local version
			if(identities.size() > 0){
				ObjectMapper mapper = new ObjectMapper();
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				URL urlSaveFile = classLoader.getResource("identitiesLocal.json");
				File file = new File(urlSaveFile.toURI().getPath());
				mapper.writeValue(file,identities);
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
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return identities;
	}

}
