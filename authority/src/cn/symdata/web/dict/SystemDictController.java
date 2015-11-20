package cn.symdata.web.dict;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.symdata.common.DataEnum.ErrorCode;
import cn.symdata.common.Message;
import cn.symdata.entity.SystemDict;
import cn.symdata.service.SystemDictService;
import cn.symdata.vo.SystemDictVo;
import cn.symdata.web.BaseController;

@Controller
@RequestMapping("/dict")
public class SystemDictController extends BaseController {

	@Autowired
	private SystemDictService systemDictService;
	
	@RequestMapping(value = "/list")
	public String findAllList(SystemDictVo systemDictVo,Integer page,ModelMap model){
		Page<SystemDict> list = systemDictService.findAllList(systemDictVo, page);
		model.addAttribute("page", list);
		return "dict/list";
	}
	
	/**
	 * 
	 * @Title: saveSystemDict 
	 * @Description: 添加数据字典页面
	 * @Autohr panpengxu#zymdata.cn
	 * @param @return   
	 * @return String  
	 * @throws 
	 * 2015年10月30日下午16:33:27
	 */
	@RequestMapping(value = "/perAdd")
	public String perAddSystemDict(){
		return "dict/add";
	}
	
	@RequestMapping(value = "/add")
	public @ResponseBody Message addSystemDict(SystemDict systemDict){
		Message msg = new Message();
		if(!StringUtils.isNotBlank(systemDict.getDictCode())||!StringUtils.isNotBlank(systemDict.getDictName())||
		   !StringUtils.isNotBlank(systemDict.getDictType())||!StringUtils.isNotBlank(systemDict.getDictTypeName())){
			msg.setMessage(ErrorCode.ERR1001.getDescription());
			return msg;
		}
		if(systemDict.getOrderId()==null){
			systemDict.setOrderId(0);
		}
		systemDictService.saveSystemDict(systemDict);
		msg.setDefaultSuccess(null);
		return msg;
	}
	/**
	 * 
	 * @Title: updateSystemDict 
	 * @Description: 跳转修改数据字典页面
	 * @Autohr panpengxu#zymdata.cn
	 * @param @return   
	 * @return String  
	 * @throws 
	 * 2015年10月30日下午16:35:21
	 */
	@RequestMapping(value = "/perEdit")
	public String perEditSystemDict(String id,ModelMap model){
		SystemDict systemDict = systemDictService.findOne(id);
		model.addAttribute("systemDict", systemDict);
		return "dict/edit";
	}
	
	/**
	 * 
	 * @Title: editSystemDict 
	 * @Description: 修改数据字典
	 * @Autohr panpengxu#zymdata.cn
	 * @param @param systemDict
	 * @param @return   
	 * @return TransDto  
	 * @throws 
	 * 2015年10月30日下午16:45:14
	 */
	@RequestMapping(value = "/edit")
	public @ResponseBody Message editSystemDict(SystemDict systemDict){
		Message msg= new Message();
		if(systemDict==null||!StringUtils.isNotBlank(systemDict.getId())){
			msg.setMessage(ErrorCode.ERR1001.getDescription());
			return msg;
		}
		SystemDict dict = systemDictService.findOne(systemDict.getId());
		if(dict==null){
			msg.setMessage(ErrorCode.ERR1001.getDescription());
			return msg;
		}
		systemDictService.updateSystemDict(systemDict,dict);
		msg.setDefaultSuccess(null);
		return msg;
	}
}
