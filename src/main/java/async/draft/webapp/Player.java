package async.draft.webapp;

public class Player {
	private String name;
	private String email;
	private Identity corp;
	private Identity runner;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Identity getCorp() {
		return corp;
	}
	public void setCorp(Identity corp) {
		this.corp = corp;
	}
	public Identity getRunner() {
		return runner;
	}
	public void setRunner(Identity runner) {
		this.runner = runner;
	}
}
