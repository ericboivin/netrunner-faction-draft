package async.draft.webapp;

public class Draft {
	
	private String code;
	private int id;
	public Draft(int id, String code){
		this.code=code;
		this.id=id;
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
	
	
}
