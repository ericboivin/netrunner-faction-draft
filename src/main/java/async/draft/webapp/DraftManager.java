package async.draft.webapp;

import java.util.Map;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DraftManager {

	private static DraftManager instance = new DraftManager();

	private Map<String,Draft> drafts;

	private IDraftDAO dao;
	
	private DraftManager() {
		dao = new PostgreDraftsDAO();
	}

	public static DraftManager getInstance() {
		return instance;
	}
	
	public void getAllDrafts(){
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("Spring-config.xml");
	 
	        IDraftDAO draftDAO = (IDraftDAO) context.getBean("draftDAO");
	 
	        Draft draft = draftDAO.getDraftByID(1);
	        System.out.println(draft.getCode());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(generateRandomDraftName());
		
		
	        
		//DraftManager mgr = DraftManager.getInstance();
		
		
	}

	public static String generateRandomDraftName() {
		Random random = new Random();
		StringBuffer result = new StringBuffer();
		int numberWords = 3;
		for (int i = 0; i < numberWords; i++) {
			char[] word = new char[random.nextInt(8) + 3];
			for (int j = 0; j < word.length; j++) {
				word[j] = (char) ('a' + random.nextInt(26));
			}
			result.append(new String(word));
			if (i != numberWords - 1) {
				result.append('-');
			}
		}
		return result.toString();
	}

}
