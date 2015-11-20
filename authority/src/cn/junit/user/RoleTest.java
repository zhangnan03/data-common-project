package cn.junit.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.exception.DatabaseException;
import cn.symdata.entity.Role;
import cn.symdata.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = false)
@Transactional
public class RoleTest {
	@Autowired
	private RoleService roleService;
	/**
	 *
	 *@throws DatabaseException 
	 * @Description:测试角色添加功能
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月1日  下午5:48:10
	 *@Version:1.0
	 */
	@Test
	public void testRoleAdd() throws DatabaseException{
		Role role = new Role();
		role.setCode("systemManager");
		role.setCreator("402882e74f88439d");
		role.setName("管理员");
		role.setDescription("系统维护");
		roleService.save(role);
	}
}
