package cn.junit.user;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.exception.DatabaseException;
import cn.symdata.entity.Menu;
import cn.symdata.entity.Role;
import cn.symdata.service.MenuService;
import cn.symdata.service.RoleService;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = false)
@Transactional
public class MenuTest {
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	/**
	 *
	 *@throws DatabaseException 
	 * @Description:测试菜单角色授权
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月1日  下午5:48:10
	 *@Version:1.0
	 */
	@Test
	public void testMenuAdd() throws DatabaseException{
		Menu menu = new Menu();
		menu.setCode("systemManager");
		menu.setCreator("45e50b45c28e856c");
		menu.setName("管理员");
		menu.setDescription("系统维护");
		List<Role> roles = Lists.newArrayList();
		Role role = roleService.findOne("bf65b6cf65b68624");
		roles.add(role);
		menu.setRoleList(roles);
		menuService.save(menu);
	}
	public static void main(String[] args){
		Set<Menu> menuSet = new TreeSet<Menu>();
		
		Menu menu2 = new Menu();
		menu2.setMenuSort(2);
		menu2.setName("2");
		menuSet.add(menu2);
		Menu menu1 = new Menu();
		menu1.setMenuSort(1);
		menu1.setName("1");
		menuSet.add(menu1);
		Menu menu3 = new Menu();
		menu3.setMenuSort(3);
		menu3.setName("3");
		menuSet.add(menu3);
		
		for(Menu menu:menuSet){
			System.out.println("name:"+menu.getName()+">>>>sort:"+menu.getMenuSort());
		}
	}
}
