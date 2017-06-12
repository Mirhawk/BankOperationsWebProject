package pojo;

public class LoginPojo {
	int userID;
	String userName;
	String password;
	String emailID;
	
	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public String getEmailID() {
		return emailID;
	}


	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LoginPojo(int userID, String userName, String password, String emailID) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.emailID = emailID;
	}
	
}
