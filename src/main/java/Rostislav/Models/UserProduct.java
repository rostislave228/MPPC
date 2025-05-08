package Rostislav.Models;

public class UserProduct {
	private User user;
    private PrintedProduct pp;
	public UserProduct(User user2, PrintedProduct temp) {
		this.user = user2;
		this.pp = temp;
	}
	public User getUser() {
		return user;
	}
	public PrintedProduct getPp() {
		return pp;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setPp(PrintedProduct pp) {
		this.pp = pp;
	}
}
