
package cn.symdata.web.user;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.symdata.common.Message;
import cn.symdata.common.exception.DatabaseException;
import cn.symdata.common.utils.JsonMapper;
import cn.symdata.dto.TransDto;
import cn.symdata.entity.Menu;
import cn.symdata.entity.Role;
import cn.symdata.service.MenuService;
import cn.symdata.service.PowerService;
import cn.symdata.service.RoleService;
import cn.symdata.web.BaseController;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:角色管理
 *@Description:
 *@Author:zhangnan#symdata
 *@Since:2015年9月8日  下午3:28:16
 *@Version:1.0
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController{
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private PowerService controlService;
	/**
	 *@param page
	 *@param model
	 *@return
	 *@Description:角色列表
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:28:50
	 *@Version:1.0
	 */
	@RequiresPermissions("role:view")
	@RequestMapping(value = "/list")
	public String list(Role role,Integer page, ModelMap model) throws DatabaseException {
		Pageable pageable = new PageRequest(page != null ? page.intValue() - 1 : 0, 10);
		Page<Role> pageInfo = roleService.findRoleByHql(role,pageable);
		model.addAttribute("page",pageInfo);
		model.addAttribute("role", role);
		return "role/list";
	}
	/**
	 *@param model
	 *@return
	 *@Description:添加角色信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:36:42
	 *@Version:1.0
	 */
	@RequiresPermissions("role:add")
	@RequestMapping(value = "/add")
	public String perAdd(Model model) throws DatabaseException {
		return "role/add";
	}
	/**
	 *@param role
	 *@param redirectAttributes
	 *@param model
	 *@return
	 *@Description:保存角色信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:36:58
	 *@Version:1.0
	 */
	@RequiresPermissions("role:add")
	@RequestMapping(value = "/save")
	public @ResponseBody Message add(Role role,TransDto dto) throws DatabaseException {
		return roleService.save(role);
	}
	/**
	 *@param id
	 *@param model
	 *@return
	 *@Description:编辑角色信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:37:15
	 *@Version:1.0
	 */
	@RequiresPermissions("role:edit")
	@RequestMapping(value = "/edit")
	public String perModify(String id,Model model) throws DatabaseException {
		Role role = roleService.findOne(id);
		model.addAttribute("role", role);
		return "role/edit";
	}
	/**
	 *@param role
	 *@param redirectAttributes
	 *@param model
	 *@return
	 *@Description:更新角色信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:37:41
	 *@Version:1.0
	 */
	@RequiresPermissions("role:edit")
	@RequestMapping(value = "/update")
	public @ResponseBody Message update(Role role) throws DatabaseException {
		return roleService.update(role);
	}
	/**
	 *@param name
	 *@return
	 *@Description:验证角色名称是否重复
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:31:52
	 *@Version:1.0
	 */
	@RequestMapping(value = "/checkCode")
	public @ResponseBody boolean checkName(String id,String code) throws DatabaseException {
		return roleService.findByCodeAndId(code, id);
	}
	
	/**
	 *@param id
	 *@param redirectAttributes
	 *@return
	 *@Description:删除角色信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:38:01
	 *@Version:1.0
	 */
	@RequiresPermissions("role:enable")
	@RequestMapping(value = "/enable")
	public @ResponseBody Message enable(String id,TransDto dto) throws DatabaseException {
		return roleService.updateIsEnable(id);
	}
	
	/**
	 * 
	 * @Title: selectMenuPowerList 
	 * @Description: TODO
	 * @Autohr guoxuelian#zymdata.cn
	 * @param @param menuId
	 * @param @param parentIds
	 * @param @param model
	 * @param @return
	 * @param @throws DatabaseException   
	 * @return String  
	 * @throws 
	 * 2015年9月21日下午2:57:25
	 */
	@RequiresPermissions("role:selectMenuPowerList")
	@RequestMapping(value = "/showMenuPowerList")
	public String showMenuPowerList(String roleId,ModelMap model) throws DatabaseException{
		List<Menu> pageInfo = roleService.showMenuPowerList(roleId);
		List<Menu> pageList = menuService.findAll();
		model.addAttribute("menuAll",    JsonMapper.nonDefaultMapper().toJson(pageList));
		model.addAttribute("menuSelect", JsonMapper.nonDefaultMapper().toJson(pageInfo));
		model.addAttribute("roleId",     roleId);
		return "role/menu_power_list";
	}
	
	/**
	 * 
	 * @Title: authorizeMenuPowerOperator 
	 * @Description:角色菜单授权
	 * @Autohr guoxuelian#zymdata.cn
	 * @param @param menuIds
	 * @param @param roleId
	 * @param @param model
	 * @param @return
	 * @param @throws DatabaseException   
	 * @return String  
	 * @throws 
	 * 2015年9月22日上午11:19:43
	 */
	@RequiresPermissions("role:authorizeMenuPowerOperator")
	@RequestMapping(value = "/authorizeMenuPowerOperator")
	public @ResponseBody Message authorizeMenuPowerOperator(String menuIds,String roleId,ModelMap model) throws DatabaseException{
		return roleService.authorizeMenuPowerOperator(menuIds,roleId);
	}
}
