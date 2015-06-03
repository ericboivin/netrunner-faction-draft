package async.draft.webapp;

public class DraftPick implements Comparable<DraftPick> {
	private String token;
	private String draftCode;
	private String playerName;
	private String side;
	private String pick;
	private int number;
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public String getPick() {
		return pick;
	}
	public void setPick(String pick) {
		this.pick = pick;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDraftCode() {
		return draftCode;
	}
	public void setDraftCode(String draftCode) {
		this.draftCode = draftCode;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public int compareTo(DraftPick other) {
		return number - other.getNumber();
	}
}
