package comeread.model;

public class User {

	private int uid;
	private String username;
	private String email;
	private String password;
	private String type = "regular";
	private String quote;

	public User(int uid, String username, String email, String password, String quote) {
		this.uid = uid;
		this.username = username;
		this.email = email;
		this.password = password;
		this.quote = quote;
	}
	
	public User(){}
	
	public int getUid() {return uid;}
	public String getUsername() {return username;}
	public String getEmail() {return email;}
	public String getPassword() {return password;}
	public String getType() {return type;}
	public String getQuote() {return quote;}
	
	public void setUid(int uid) {this.uid = uid;}
	public void setUsername(String username) {this.username = username;}
	public void setEmail(String email) {this.email = email;}
	public void setPassword(String password) {this.password = password;}
	public void setType(String type) {this.type = type;}
	public void setQuote(String quote) {this.quote = quote;}
}
