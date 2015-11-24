package cn.junit.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.core.JedisTemplate;
import cn.symdata.common.core.RedisObjectType;
import cn.symdata.common.utils.JsonMapper;
import cn.symdata.common.utils.UUIDGenerator;
import cn.symdata.entity.Account;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = false)
@Transactional
public class RedisTest {
	private final static JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	/**
	 *
	 *@throws Exception 
	 * @Description:测试用户查询功能
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月1日  下午5:48:25
	 *@Version:1.0
	 */
	@Test
	public void testRedisAdd() throws Exception{
		
	}
}
