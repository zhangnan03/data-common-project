
package cn.symdata.web.user;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.symdata.common.Message;
import cn.symdata.common.exception.DatabaseException;
import cn.symdata.common.utils.JsonMapper;
import cn.symdata.dto.TransDto;
import cn.symdata.entity.Menu;
import cn.symdata.entity.Role;
import cn.symdata.entity.User;
import cn.symdata.service.MenuService;
import cn.symdata.service.RoleService;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:
 *@Description:菜单管理
 *@Author:zhangnan#symdata
 *@Since:2015年9月8日  下午3:16:26
 *@Version:1.0
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;
	/**
	 *@param page
	 *@param model
	 *@return
	 *@Description:菜单列表
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:23:22
	 *@Version:1.0
	 */
	@RequiresPermissions("menu:list")
	@RequestMapping(value = "/list")
	public String list(Integer page, ModelMap model) throws DatabaseException {
		List<Menu> pageList = menuService.findAll();
		model.addAttribute("page",JsonMapper.nonDefaultMapper().toJson(pageList));
		return "menu/list";
	}
	
	/**
	 *@param menu
	 *@param redirectAttributes
	 *@param model
	 *@return
	 *@Description:保存菜单信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:25:53
	 *@Version:1.0
	 */
	@RequiresPermissions("menu:add")
	@RequestMapping(value = "/add")
	public @ResponseBody Message add(Menu menu,TransDto dto) throws DatabaseException {
		return menuService.save(menu);
	}
	
	/**
	 *@param menu
	 *@param redirectAttributes
	 *@param model
	 *@return
	 *@Description:更新菜单信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:26:21
	 *@Version:1.0
	 */
	@RequiresPermissions("menu:update")
	@RequestMapping(value = "/update")
	public @ResponseBody Message modify(Menu menu,TransDto dto) throws DatabaseException {
		return menuService.update(menu);
	}
	/**
	 *@param code
	 *@return
	 *@Description:验证编码是否重复
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:26:37
	 *@Version:1.0
	 */
	@RequestMapping(value = "/checkCode")
	public @ResponseBody boolean checkCode(String id,String code) throws DatabaseException {
		return menuService.findByCodeAndId(code, id);
	}
	/**
	 *@param id
	 *@param redirectAttributes
	 *@return
	 *@Description:删除菜单
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:20:30
	 *@Version:1.0
	 */
	@RequiresPermissions("user:enable")
	@RequestMapping(value = "/enable")
	public @ResponseBody Message enable(User user,TransDto dto) throws DatabaseException {
		return menuService.updateMenuIsEnable(user.getId());
	}
	
	@RequiresPermissions("menu:authorizeMenuRoleList")
	@RequestMapping(value = "/authorizeMenuRoleList")
	public String authorizeMenuRoleList(String menuId,String parentIds,ModelMap model) throws DatabaseException{
		List<Role> pageInfo = menuService.authorizeMenuRoleList(menuId);
		model.addAttribute("pageInfo",     pageInfo);
		model.addAttribute("menuId",   menuId);
		model.addAttribute("parentIds",parentIds);
		return "menu/power_operate";
	}
	
	/**
	 * 
	 * @Title: authorizeOperate 
	 * @Description: 授权操作
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param id 用户ID
	 * @param @param ids 角色id列表
	 * @param @param model
	 * @param @param dto
	 * @param @return   
	 * @return TransDto  
	 * @throws 
	 * 2015年9月12日上午10:17:47
	 */
	@RequiresPermissions("menu:authorizeMenuRoleOperate")
	@RequestMapping(value = "/authorizeMenuRoleOperate")
	public @ResponseBody Message authorizeMenuRoleOperate(String id,String ids,ModelMap model,TransDto dto) throws DatabaseException{
		return menuService.authorizeMenuRoleOperate(id,ids);
	}
}
