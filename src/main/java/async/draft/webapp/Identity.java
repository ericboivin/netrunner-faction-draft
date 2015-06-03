package async.draft.webapp;

public class Identity {
	
	private String name;
	private String code;
	private String faction;
	private String side; 
	private String takenBy;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSide(){
		return side;
	}
	public void setSide(String side){
		this.side = side;
	}
	public String getFaction() {
		return faction;
	}
	public void setFaction(String faction) {
		this.faction = faction;
	}
	public String getTakenBy() {
		return takenBy;
	}
	public void setTakenBy(String takenBy) {
		this.takenBy = takenBy;
	}
	public String toString(){
		return name;
	}
}
