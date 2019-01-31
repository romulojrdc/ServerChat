package chat.conf.client;

public class User {
	private final static String PRE_NAME = "GUEST";
	public User() {
		
	}
	public User(int count) {
		
		username = getNewName(count);
	}
	
	public static String getNewName(int count) {
		return PRE_NAME + count;
	}
	private String username;
	private String pwd;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
}
