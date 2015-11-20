package cn.symdata.common.exception;


/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:zplay.cn
 *@Title:
 *@Description:
 *@Author:wangdezhen#zplay.cn
 *@Since:2015年3月12日  下午4:44:50
 *@Version:1.0
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int status = 2005;
	
	public ServiceException() {
	}

	public ServiceException(int status) {
		this.status = status;
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(int status, String message) {
		super(message);
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
}
