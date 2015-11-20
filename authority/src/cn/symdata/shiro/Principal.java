package cn.symdata.shiro;

import java.io.Serializable;

/**
 * 
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:用户身份信息
 *@Description:
 *@Author:zhangnan#symdata
 *@Since:2015年9月1日  下午3:22:32
 *@Version:1.0
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private String id;

	/** 用户名 */
	private String username;

	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(String id, String username) {
		this.id = id;
		this.username = username;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}

}