package async.draft.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DraftManager {

	private static DraftManager instance = new DraftManager();

	private IDraftDAO dao;

	private DraftManager() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"Spring-config.xml");

		dao = (IDraftDAO) context.getBean("draftDAO");
	}

	public static DraftManager getInstance() {
		return instance;
	}

	public Draft getDraft(String code) {
		return dao.getDraft(code);
	}
	
	public DraftPick getPick(Draft draft, String token){
		List<DraftPick> picks = draft.getPicks();
		for (int i=0;i<picks.size();i++){
			if (token.equals(picks.get(i).getToken())){
				return picks.get(i);
			}
		}
		return null;
	}
	
	public void savePick(DraftPick pick, Draft draft){
		for (int i=0;i<draft.getPlayers().size();i++){
			if (pick.getPlayerName().equals(draft.getPlayers().get(i).getName())){
				if (pick.getSide().equals("Corp")){
					draft.getPlayers().get(i).setCorp(IdentityManager.getInstance().getIdentity(pick.getPick()));
				}else{
					draft.getPlayers().get(i).setRunner(IdentityManager.getInstance().getIdentity(pick.getPick()));
				}
			}
		}
		dao.saveDraft(draft);
		callNextPlayer(draft);
	}
	
	public Draft createDraft(List<Player> players) {
		Draft draft = new Draft();
		long seed = System.nanoTime();
		Collections.shuffle(players, new Random(seed));
		draft.setPlayers(players);
		draft.setCode(generateRandomDraftName());
		
		List<DraftPick> picks = new ArrayList<DraftPick>();
		
		for (int i=0;i<players.size();i++){
			DraftPick pick = new DraftPick();
			pick.setPlayerName(players.get(i).getName());
			pick.setSide("Corp");
			pick.setToken(generateRandomToken());
			pick.setNumber(i);
			picks.add(pick);
			
			DraftPick pick2 = new DraftPick();
			pick2.setPlayerName(players.get(i).getName());
			pick2.setSide("Runner");
			pick2.setToken(generateRandomToken());
			//Snake draft: first corp pick = last runner pick
			pick2.setNumber(1000-i);
			picks.add(pick2);
		}
		draft.setPicks(picks);
		
		dao.saveDraft(draft);
		
		callNextPlayer(draft);
		
		return draft;
	}

	public void callNextPlayer(Draft draft) {
		List<DraftPick> picks = draft.getPicks();
		
		if (picks != null){
			Collections.sort(picks);
			for (int i=0;i<picks.size();i++){
				if(picks.get(i).getPick() == null){
					
					for (int j=0;j<draft.getPlayers().size();j++){
						if (draft.getPlayers().get(j).getName().equals(picks.get(i).getPlayerName())){
							Player player = draft.getPlayers().get(j);
							System.out.println(picks.get(i).getToken());
							SendgridMail.sendMail(player, picks.get(i).getToken(), draft.getCode());
							return;
						}
					}		
				}
			}
			//Draft completed
		}
	}

	private String generateRandomDraftName() {
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

	private String generateRandomToken() {
		Random random = new Random();
		char[] word = new char[32];
		for (int j = 0; j < 32; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		return new String(word);
	}

	public List<Identity> filterTaken(Draft draft, List<Identity> allSideIdentities) {
		Map<String, String> takenIds = new HashMap<String, String>();
		List<DraftPick> picks = draft.getPicks();
		for (int i=0;i<picks.size();i++){
			if (picks.get(i).getPick() != null){
				takenIds.put(picks.get(i).getPick(), picks.get(i).getPlayerName());
			}
		}
		for (int j=0;j<allSideIdentities.size();j++){
			String taker = takenIds.get(allSideIdentities.get(j).getCode());
			if (taker != null){
				allSideIdentities.get(j).setTakenBy(taker);
			}
		}
		return allSideIdentities;
	}
	
	public boolean isTaken(Draft draft, String idCode){
		List<DraftPick> picks = draft.getPicks();
		for(int i=0;i<picks.size();i++){
			if (picks.get(i).getPick() != null && picks.get(i).getPick().equals(idCode)){
				return true;
			}
		}
		return false;
	}

}

