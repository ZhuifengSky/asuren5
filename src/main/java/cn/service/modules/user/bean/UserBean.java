package cn.service.modules.user.bean;

public class UserBean {

	private Integer id;
    private String userName;
    private String avatar;
    private String loginDate;
    private String loginSessionId;
    private String email;
    private boolean activated;
	
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getLoginSessionId() {
		return loginSessionId;
	}
	public void setLoginSessionId(String loginSessionId) {
		this.loginSessionId = loginSessionId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
    
    

}
