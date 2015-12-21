package com.symdata.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.symdata.service.DubboRemoteService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = false)
//@Transactional
public class DubboTest {
	@Autowired
	private DubboRemoteService dubboRemoteService;
	/**
	 *
	 *@Description:测试dubbo查询功能
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月1日  下午5:48:25
	 *@Version:1.0
	 */
	@Test
	public void testGetMsg(){
		System.out.println(dubboRemoteService.publishMessage());
	}
	@Test
	public void testGetPerson(){
		System.out.println(dubboRemoteService.publishPerson("100").getName());
	}
}
