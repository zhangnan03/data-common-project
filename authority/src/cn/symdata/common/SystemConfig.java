package cn.symdata.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:
 *@Description:系统配置信息
 *@Author:zhangnan#symdata
 *@Since:2015年9月6日  下午9:12:21
 *@Version:1.0
 */
@Configuration
public class SystemConfig {
	/**
	 * 系统标识
	 */
	@Value("#{system.SYSTEM_ID}")
	private String systemId;
	
	@Value("#{system.DEFAULT_PASSWORD}")
	private String defaultPassword;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	
}
