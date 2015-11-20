
package cn.symdata.web.user;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import cn.symdata.entity.Power;
import cn.symdata.entity.User;
import cn.symdata.service.MenuService;
import cn.symdata.service.PowerService;
import cn.symdata.web.BaseController;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:
 *@Description:控件按钮管理
 *@Author:zhangnan#symdata
 *@Since:2015年9月8日  下午2:54:39
 *@Version:1.0
 */
@Controller
@RequestMapping(value = "/power")
public class PowerController extends BaseController{
	@Autowired
	private PowerService powerService;
	@Autowired
	private MenuService menuService;
	/**
	 *@param resourceId
	 *@param page
	 *@param model
	 *@return
	 *@Description:控件列表
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午2:58:33
	 *@Version:1.0
	 */
	@RequiresPermissions("power:view")
	@RequestMapping(value = "/list")
	public String list(Power power,Integer page, Model model) throws DatabaseException {
		List<Menu> menuList  = menuService.findAll();
		Page<Power> pageList = powerService.findPowerByHql(power,page);
		model.addAttribute("menuList", JsonMapper.nonDefaultMapper().toJson(menuList));//菜单列表
		model.addAttribute("page",pageList);//按钮列表
		model.addAttribute("power", power);
		return "power/list";
	}
	
	/**
	 *@param model
	 *@return
	 *@Description:新增控件信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午2:59:31
	 *@Version:1.0
	 */
	@RequiresPermissions("power:add")
	@RequestMapping(value = "/powerAddView")
	public String powerAddView(Model model) throws DatabaseException {
		List<Menu> pageList = menuService.findAll();
		model.addAttribute("page", JsonMapper.nonDefaultMapper().toJson(pageList));
		return "power/add";
	}
	/**
	 *@param power
	 *@param redirectAttributes
	 *@param model
	 *@return
	 *@Description:保存控件信息
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:56:06
	 *@Version:1.0
	 */
	@RequiresPermissions("power:add")
	@RequestMapping(value = "/save")
	public @ResponseBody Message savePower(Power power,TransDto dto) throws DatabaseException {
		return powerService.save(power);
	}
	
	@RequiresPermissions("power:edit")
	@RequestMapping(value = "/edit")
	public String perModify(Power power, ModelMap model) throws DatabaseException {
		List<Menu> pageList = menuService.findAll();
		power = powerService.findOne(power.getId());
		model.addAttribute("power", power);
		model.addAttribute("page", JsonMapper.nonDefaultMapper().toJson(pageList));
		return "power/edit";
	}
	
	@RequiresPermissions("power:update")
	@RequestMapping(value = "/update")
	public @ResponseBody Message update(Power power,TransDto dto) throws DatabaseException {
		return powerService.update(power);
	}
	
	/**
	 * 
	 * @Title: enable 
	 * @Description: 删除按钮
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param user
	 * @param @param dto
	 * @param @return   
	 * @return TransDto  
	 * @throws 
	 * 2015年9月12日下午4:36:09
	 */
	@RequiresPermissions("power:enable")
	@RequestMapping(value = "/enable")
	public @ResponseBody Message enable(User user,TransDto dto) throws DatabaseException {
		return powerService.updatePowerIsEnable(user.getId());
	}
	
	/**
	 *@param code
	 *@return
	 *@Description:检查编码是否重复
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午3:57:06
	 *@Version:1.0
	 */
	@RequestMapping(value = "checkCode")
	public @ResponseBody boolean checkCode(String id,String code) throws DatabaseException {
		return powerService.findByCodeAndId(code, id);
	}
}
