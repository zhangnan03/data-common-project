package cn.symdata.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.DataEnum.ErrorCode;
import cn.symdata.common.Message;
import cn.symdata.common.exception.DatabaseException;
import cn.symdata.dao.MenuDao;
import cn.symdata.dao.RoleDao;
import cn.symdata.entity.Menu;
import cn.symdata.entity.Role;
import cn.symdata.service.MenuService;
import cn.symdata.service.RoleService;
import cn.symdata.shiro.AuthenticationRealm;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService{
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AuthenticationRealm authenticationRealm;
	
	@Override
	public Page<Menu> findAll(Pageable pageable) throws DatabaseException{
		return menuDao.findAll(pageable);
	}
	
	@Override
	public List<Menu> findAll() throws DatabaseException {
		return (List<Menu>) menuDao.findByAll();
	}
	
	@Override
	public Menu findOne(String id) throws DatabaseException {
		return menuDao.findOne(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message update(Menu menu) throws DatabaseException{
		Message msg = new Message();
		if(!StringUtils.isNotBlank(menu.getId())||!StringUtils.isNotBlank(menu.getCode())||!StringUtils.isNotBlank(menu.getName())||!StringUtils.isNotBlank(menu.getSystemMark())){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		Menu menuParam = menuDao.findOne(menu.getId());
		if(menuParam==null){
			msg.setError(ErrorCode.ERR1004.getCode(), ErrorCode.ERR1004.getDescription(), null);
			return msg;
		}
		menuParam.setId(menu.getId());
		menuParam.setCode(menu.getCode());
		menuParam.setName(menu.getName());
		menuParam.setSystemMark(menu.getSystemMark());
		menuParam.setDescription(menu.getDescription());
		menuParam.setMenuFlag(menu.getMenuFlag());
		menuParam.setUrl(menu.getUrl());
		menuParam.setMenuSort(menu.getMenuSort());
		menuDao.save(menuParam);
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	public Menu findByCode(String code) throws DatabaseException{
		return menuDao.findByCode(code);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message updateMenuIsEnable(String id) throws DatabaseException {
		Message msg = new Message();
		if(!StringUtils.isNotBlank(id)){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		menuDao.updateMenuIsEnable(id);
		msg.setDefaultSuccess(null);
		return msg;
	}

	@Override
	@Transactional(readOnly = false)
	public Message save(Menu menu) {
		Message msg = new Message();
		if(!StringUtils.isNotBlank(menu.getCode())||!StringUtils.isNotBlank(menu.getName())||!StringUtils.isNotBlank(menu.getSystemMark())){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		menuDao.save(menu);
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	public List<Role> authorizeMenuRoleList(String menuId) throws DatabaseException{
		List<Role> pageInfo = roleDao.findByEnable();
		for (Role role : pageInfo) {
			String menus = role.getMenus();
			if(StringUtils.isNotBlank(menuId)&&menus.contains(menuId))
				role.setIsChecked(1);
		}
		return pageInfo;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message authorizeMenuRoleOperate(String menuIds,String roleIds) throws DatabaseException {
		Message msg = new Message();
		if(!StringUtils.isNotBlank(menuIds)){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		List<String> menuIdList = Splitter.on(",").trimResults().splitToList(menuIds.substring(1));
		for (String menuId : menuIdList) {
			List<Role> roleList = Lists.newArrayList();
			Menu menu = menuDao.findOne(menuId);
			if(menu!=null){
				if(StringUtils.isNotBlank(roleIds)){
					List<String> list = Splitter.on(",").trimResults().splitToList(roleIds.substring(1));
					for (String string : list) {
						if(StringUtils.isNotBlank(string)){
							Role role = roleService.findOne(string);
							if(role!=null) roleList.add(role);
						}
					}
				}
				menu.setRoleList(roleList);
				menuDao.save(menu);
			}
		}
		msg.setDefaultSuccess(null);
		return msg;
	}

	@Override
	public List<Menu> findBySystemMark(String systemMark)
			throws DatabaseException {
		return menuDao.findBySystemMark(systemMark);
	}

	@Override
	public boolean findByCodeAndId(String code, String id) throws DatabaseException {
		Menu menu = null;
		code  = StringUtils.trimToEmpty(code);
		if(Strings.isNullOrEmpty(code)){
			return false;
		}
		if(StringUtils.isNotBlank(id)){
			menu = menuDao.findByCodeAndId(code,id);
		}else{
			menu = menuDao.findByCode(code);
		}
		if(menu!=null){
			return false;
		}
		return true;
	}
}
