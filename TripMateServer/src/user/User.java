package user;


public class User {
	private String userCode;
	private String userID;
	private String userPassword;
	private String userNickname;
	private int userAge;
	private int userGender;
	/*private String userInterest1;
	private String userInterest2;
	private String userInterest3;*/
	private String userEmail;
	public User() {
		
	}
	
	
	public User(String userID, String userPassword, String userNickname, int userAge, int userGender,
			/*String userInterest1, String userInterest2, String userInterest3,*/ String userEmail) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userNickname = userNickname;
		this.userAge = userAge;
		this.userGender = userGender;
		/*this.userInterest1 = userInterest1;
		this.userInterest2 = userInterest2;
		this.userInterest3 = userInterest3;*/
		this.userEmail = userEmail;
	}
	
	
	public String getUserCode() {
		return userCode;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public int getUserGender() {
		return userGender;
	}
	public void setUserGender(int userGender) {
		this.userGender = userGender;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	/*public String getUserInterest1() {
		return userInterest1;
	}
	public void setUserInterest1(String userInterest1) {
		this.userInterest1 = userInterest1;
	}
	public String getUserInterest2() {
		return userInterest2;
	}
	public void setUserInterest2(String userInterest2) {
		this.userInterest2 = userInterest2;
	}
	public String getUserInterest3() {
		return userInterest3;
	}
	public void setUserInterest3(String userInterest3) {
		this.userInterest3 = userInterest3;
	}*/


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}