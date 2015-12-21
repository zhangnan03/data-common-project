package com.symdata.service;

import com.symdata.entity.Person;

/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:
 *@Description:dubbo生产者
 *@Author:zhangnan#symdata
 *@Since:2015年12月21日  下午1:59:32
 *@Version:1.0
 */
public interface DubboRemoteService {
	/**
	 *@return
	 *@Description:发布消息
	 *@Author:zhangnan#symdata
	 *@Since:2015年12月21日  下午1:59:32
	 *@Version:1.0
	 */
	public abstract String publishMessage();
	/**
	 * 
	 *@return
	 *@Description:测试传递一个对象
	 *@Author:zhangnan#symdata
	 *@Since:2015年12月21日  下午2:48:48
	 *@Version:1.0
	 */
	public abstract Person publishPerson(String id);
}
