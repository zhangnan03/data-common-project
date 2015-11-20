package cn.symdata.dto;

public class UserDto{

	/**
	 * ID
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 是否修改密码：0：否；1：修改
	 */
	private String isUpdatePwd;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsUpdatePwd() {
		return isUpdatePwd;
	}
	public void setIsUpdatePwd(String isUpdatePwd) {
		this.isUpdatePwd = isUpdatePwd;
	}
	
}
