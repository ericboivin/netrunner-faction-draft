package async.draft.webapp;

public class Identity {
	public static final int SIDE_CORP = 1;
	public static final int SIDE_RUNNER = 2;
	
	private String name;
	private String imgsrc;
	private int side;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public int getSide(){
		return side;
	}
	public void setSide(int side){
		this.side = side;
	}
	
	public String toString(){
		return name;
	}
}
