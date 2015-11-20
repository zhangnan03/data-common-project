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
import cn.symdata.entity.DataField;
import cn.symdata.entity.Menu;
import cn.symdata.entity.User;
import cn.symdata.service.DataFieldService;
import cn.symdata.service.MenuService;

@Controller
@RequestMapping(value = "/dataField")
public class DataFieldController {
	@Autowired
	private DataFieldService dataFieldService;
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 
	 * @Title: list 
	 * @Description: 字段列表
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param dataField
	 * @param @param page
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws 
	 * 2015年9月12日下午2:58:16
	 */
	@RequiresPermissions("dataField:view")
	@RequestMapping(value = "/list")
	public String list(DataField dataField,Integer page, ModelMap model) throws DatabaseException {
		List<Menu> menuList  = menuService.findAll();
		Page<DataField> pageList = dataFieldService.findDataFieldByHql(dataField, page);
		model.addAttribute("menuList", JsonMapper.nonDefaultMapper().toJson(menuList));//菜单列表
		model.addAttribute("page",pageList);
		model.addAttribute("dataField", dataField);
		return "datafield/list";
	}
	
	/**
	 * 
	 * @Title: dataFieldAdd 
	 * @Description: 添加字段
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws 
	 * 2015年9月12日下午2:58:31
	 */
	@RequiresPermissions("dataField:add")
	@RequestMapping(value = "/dataFieldAdd")
	public String dataFieldAdd(Model model) throws DatabaseException {
		List<Menu> pageList = menuService.findAll();
		model.addAttribute("page", JsonMapper.nonDefaultMapper().toJson(pageList));
		return "datafield/add";
	}
	
	/**
	 * 
	 * @Title: saveDataField 
	 * @Description: 保存操作
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param dataField
	 * @param @param dto
	 * @param @return   
	 * @return TransDto  
	 * @throws 
	 * 2015年9月12日下午3:10:43
	 */
	@RequiresPermissions("dataField:add")
	@RequestMapping(value = "/save")
	public @ResponseBody Message saveDataField(DataField dataField,TransDto dto) throws DatabaseException {
		return dataFieldService.save(dataField);
	}
	
	/**
	 * 
	 * @Title: perModify 
	 * @Description: 编辑操作
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param dataField
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws 
	 * 2015年9月12日下午3:10:57
	 */
	@RequiresPermissions("dataField:edit")
	@RequestMapping(value = "/edit")
	public String editDataField(DataField dataField, ModelMap model) throws DatabaseException {
		List<Menu> pageList = menuService.findAll();
		dataField = dataFieldService.findOne(dataField.getId());
		model.addAttribute("dataField", dataField);
		model.addAttribute("page", JsonMapper.nonDefaultMapper().toJson(pageList));
		return "datafield/edit";
	}
	
	@RequiresPermissions("dataField:edit")
	@RequestMapping(value = "/update")
	public @ResponseBody Message update(DataField dataField,TransDto dto) throws DatabaseException {
		return dataFieldService.update(dataField);
	}
	
	/**
	 * 
	 * @Title: checkCode 
	 * @Description: 检验编码是否重复
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param code
	 * @param @return   
	 * @return boolean  
	 * @throws 
	 * 2015年9月12日下午4:21:46
	 */
	@RequestMapping(value = "checkCode")
	public @ResponseBody boolean checkCode(String id,String code) throws DatabaseException {
		return dataFieldService.findByCodeAndId(code, id);
	}
	
	/**
	 * 
	 * @Title: enable 
	 * @Description: 删除字段
	 * @Autohr guoxuelian#zymdata.cn
	 * @param @param user
	 * @param @param dto
	 * @param @return
	 * @param @throws DatabaseException   
	 * @return Message  
	 * @throws 
	 * 2015年9月17日下午2:40:00
	 */
	@RequiresPermissions("dataField:enable")
	@RequestMapping(value = "/enable")
	public @ResponseBody Message enable(User user,TransDto dto) throws DatabaseException {
		return dataFieldService.updateDataFieldIsEnable(user.getId());
	}
}
