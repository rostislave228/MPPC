package Rostislav.Models;

public class User {
	private int id;
    private String username;
    private String password;
    
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
   
    
	public void setId(int id) {
		this.id = id;
	}
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
	public User() {}
	public User(int id2, String username2, String password2) {
		this.id = id2;
		this.username = username2;
        this.password = password2;
	}
	
}