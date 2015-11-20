package cn.junit.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.exception.DatabaseException;
import cn.symdata.entity.Role;
import cn.symdata.entity.User;
import cn.symdata.service.RoleService;
import cn.symdata.service.UserService;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = false)
@Transactional
public class UserTest {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	/**
	 *
	 *@Description:测试用户查询功能
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月1日  下午5:48:25
	 *@Version:1.0
	 */
	@Test
	public void testUserQuery(){
//		User user = userService.findUserByUsernameAndPassword("admin","21232f297a57a5a743894a0e4a801fc3");
		Pageable pageable = new PageRequest(0, 10);
		Page<User> page;
//		page = userService.findUsersByCreateTime(pageable, null, null);
//		System.out.println(page.getContent().size());
		
	}
	/**
	 *
	 *@throws DatabaseException 
	 * @Description:测试用户添加功能
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月1日  下午5:48:10
	 *@Version:1.0
	 */
	@Test
	public void testUserAdd() throws DatabaseException{
		User user = new User();
		user.setRealName("admin");
		user.setPassword(DigestUtils.md5Hex("123456"));
		user.setUsername("ttt");
		user.setEmail("9999888@qq.com");
		user.setIsLocked(0);
		user.setLockedDate(new Date());
		user.setLoginDate(new Date());
		user.setLoginFailureCount(0);
		user.setLoginIp("101.111");
		user.setStatus(1);
		user.setCreator("1");
		user.setIsEnable(0);
		List<Role> roleList= Lists.newArrayList();
		Role role = new Role();
		role.setId("32aa1000a44ffad0");
		roleList.add(role);
		user.setRoleList(roleList);
		userService.save(user);
	}
	/**
	 *
	 *@throws DatabaseException 
	 * @Description:测试用户修改
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月1日  下午5:48:10
	 *@Version:1.0
	 */
	@Test
	public void testUserUpdate() throws DatabaseException{
		User user = new User();
		user.setPassword(DigestUtils.md5Hex("123456"));
		user.setUsername("zhangnan");
//		System.out.println(userService.updateUserPassword(user.getPassword(), user.getUsername()));
	}
	@Test
	public void test(){
		System.out.println(DigestUtils.md5Hex("admin"));
	}
	public static void main(String[] args) {
//		3ca6b1762cac2c559332a9f86ec1f9a3
		System.out.println(DigestUtils.md5Hex("123123"));
	}
}
