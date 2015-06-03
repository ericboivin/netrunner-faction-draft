package async.draft.webapp;

import java.util.List;

public class Draft {
	
	private String code;
	private int id;
	private List<Player> players;
	private List<DraftPick> picks;
	
	public Draft() {
	}
	public Draft(int id, String code) {
		this.id=id;
		this.code=code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public List<DraftPick> getPicks() {
		return picks;
	}
	public void setPicks(List<DraftPick> picks) {
		this.picks = picks;
	}
	
	public boolean isIdentityTaken(String code){
		for (int i=0;i<picks.size();i++){
			if (picks.get(i).getPick() != null && picks.get(i).getPick().equals(code)){
				return true;
			}
		}
		return false;
	}
}
