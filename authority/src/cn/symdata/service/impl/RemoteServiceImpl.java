package cn.symdata.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.DataEnum.ErrorCode;
import cn.symdata.common.exception.DatabaseException;
import cn.symdata.dao.UserDao;
import cn.symdata.dto.MenuDto;
import cn.symdata.dto.PermissionDto;
import cn.symdata.dto.TransDto;
import cn.symdata.dto.UserDto;
import cn.symdata.entity.Menu;
import cn.symdata.entity.User;
import cn.symdata.service.RemoteService;

import com.google.common.collect.Lists;

/**
 * 
 * @ClassName: RemoteInterfaceImpl 
 * @Description: 外部系统接口信息处理 
 * @author guoxuelian@symdata.cn
 * @date 2015年9月16日 下午5:09:18 
 *
 */
@Service
public class RemoteServiceImpl implements RemoteService{
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDto remoteLogin(User user,String username) throws DatabaseException {
		UserDto userDto = null;
		
		userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setRealName(user.getRealName());
		userDto.setPassword(user.getPassword());
		userDto.setIsUpdatePwd(user.getIsUpdatePwd()+"");
		return userDto;
	}
	
	@Override
	public PermissionDto permission(User user,String systemId) throws DatabaseException {
		PermissionDto permissionDto = null;
		
		permissionDto              = new PermissionDto();
		List<String> rolesList     = user.getRoles();      //查询该用户的角色
		List<String> powerList     = user.getPowers();     //查询该用户的按钮
		List<String> dataFieldList = user.getDataFields(); //查询该用户的字段
		permissionDto.setUsername(user.getUsername());
		permissionDto.setRealName(user.getRealName());
		permissionDto.setRolesList(rolesList);
		permissionDto.setPowerList(powerList);
		permissionDto.setDataFieldList(dataFieldList);
		return permissionDto;
	}

	@Override
	public List<MenuDto> getMenu(User user,String systemId) throws DatabaseException {
		List<MenuDto> menuListDto  = Lists.newArrayList();
		
		List<Menu> menuList = user.getMenuList(systemId); //查询该用户的菜单
		for (Menu menu : menuList) {
			MenuDto menuDto = new MenuDto();
			menuDto.setCode(menu.getCode());
			menuDto.setName(menu.getName());
			menuDto.setParent(menu.getParent());
			menuDto.setChildren(menu.getChildrenTmp());
			menuDto.setSystemMark(menu.getSystemMark());
			menuDto.setMenuFlag(menu.getMenuFlag());
			menuDto.setUrl(menu.getUrl());
			menuListDto.add(menuDto);
		}
		return menuListDto;
	}

	@Transactional(readOnly=false)
	@Override
	public void updatePassword(String username, String newPwd) throws DatabaseException {
		userDao.updateUserPassword(newPwd, username);
	}

}
