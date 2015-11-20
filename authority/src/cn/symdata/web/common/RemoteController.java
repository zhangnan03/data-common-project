package cn.symdata.web.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.symdata.common.DataEnum.ErrorCode;
import cn.symdata.common.exception.DatabaseException;
import cn.symdata.dto.MenuDto;
import cn.symdata.dto.PermissionDto;
import cn.symdata.dto.TransDto;
import cn.symdata.dto.UserDto;
import cn.symdata.entity.User;
import cn.symdata.service.RemoteService;
import cn.symdata.service.SystemDictService;
import cn.symdata.service.UserService;
import cn.symdata.vo.SystemDictVo;

import com.google.common.collect.Lists;

/**
 * 
 * @ClassName: RemoteController 
 * @Description: 给外部系统提供的接口，查询用户信息、修改密码、查询相关权限
 * @author guoxuelian@symdata.cn
 * @date 2015年9月14日 下午1:36:06 
 *
 */
@RestController
@RequestMapping("/remote")
public class RemoteController{
	@Autowired
	private RemoteService remoteInterface;
	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemDictService systemDictService;
	
	/**
	 *@param username
	 *@param password
	 *@return
	 *@Description:用户查询
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月6日  下午8:11:16
	 *@Version:1.0
	 */
	@RequestMapping(value = "/queryUser", method = RequestMethod.POST)
	public TransDto login(String username) throws DatabaseException {
		TransDto dto    = new TransDto();
		UserDto userDto = null;
		
		if(!StringUtils.isNotBlank(username)){
			dto.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return dto;
		}
		User user = userService.findByUsername(username);
		if(user==null){
			dto.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return dto;
		}
		userDto = remoteInterface.remoteLogin(user,username);
		dto.setDefaultSuccess(userDto);
		return dto;
	}
	
	/**
	 *@param username
	 *@param systemId 系统标识
	 *@return
	 *@Description:获得用户的权限
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月6日  下午8:11:33
	 *@Version:1.0
	 */
	@RequestMapping(value = "/permission", method = RequestMethod.POST)
	public TransDto permission(String username,String systemId) throws DatabaseException {
		TransDto dto                = new TransDto();
		PermissionDto permissionDto = null;
		
		if(!StringUtils.isNotBlank(username)||!StringUtils.isNotBlank(systemId)){
			dto.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return dto;
		}
		User user = userService.findByUsername(username);
		if(user==null){
			dto.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return dto;
		}
		permissionDto = remoteInterface.permission(user,systemId);
		dto.setDefaultSuccess(permissionDto);
		return dto;
	}
	
	/**
	 * 
	 * @Title: getMenu 
	 * @Description: TODO
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param username
	 * @param @param systemId
	 * @param @return
	 * @param @throws DatabaseException   
	 * @return TransDto  
	 * @throws 
	 * 2015年9月14日下午3:48:49
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	public TransDto getMenu(String username,String systemId) throws DatabaseException {
		TransDto dto = new TransDto();
		
		if(!StringUtils.isNotBlank(username)||!StringUtils.isNotBlank(systemId)){
			dto.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return dto;
		}
		User user = userService.findByUsername(username);
		if(user==null){
			dto.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return dto;
		}
		List<MenuDto> menuListDto = remoteInterface.getMenu(user,systemId);
		dto.setDefaultSuccess(menuListDto);
		return dto;
	}
	
	/**
	 * 
	 * @Title: modifyPassword 
	 * @Description: 修改密码
	 * @Autohr guoxuelian#symdata.cn
	 * @param @param username
	 * @param @param newPwd
	 * @param @param oldPwd
	 * @param @return
	 * @param @throws DatabaseException   
	 * @return TransDto  
	 * @throws 
	 * 2015年9月16日下午5:03:52
	 */
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public TransDto modifyPassword(String username,String newPwd,String oldPwd) throws DatabaseException{
		TransDto dto = new TransDto();
		
		if(!StringUtils.isNotBlank(username)||!StringUtils.isNotBlank(newPwd)||!StringUtils.isNotBlank(oldPwd)){//参数错误
			dto.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return dto;
		}
		User user    = userService.findByUsername(username);
		if(user==null){//找不到该用户
			dto.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return dto;
		}
		if(!oldPwd.equals(user.getPassword())){//老密码错误
			dto.setError(ErrorCode.ERR1003.getCode(), ErrorCode.ERR1003.getDescription(), null);
			return dto;
		}
		remoteInterface.updatePassword(username, newPwd);
		dto.setDefaultSuccess(null);
		return dto;
	}
	
	/**
	 *@param dictType
	 *@param dictCode
	 *@return
	 *@Description:数据字典查询
	 *@Author:panpengxu#symdata
	 *@Since:2015年11月2日 下午17:04:06 
	 *@Version:1.0
	 */
	@RequestMapping(value = "/queryDict", method = RequestMethod.POST)
	public TransDto queryDict(String dictType,String dictCode) throws DatabaseException {
		TransDto dto    = new TransDto();
		List<SystemDictVo> dictList = Lists.newArrayList();
		SystemDictVo systemDictVo   = null;
		if(StringUtils.isNotBlank(dictType)&&StringUtils.isNotBlank(dictCode)){
			systemDictVo = systemDictService.findByDictTypeAndDictCode(dictType,dictCode);
			dto.setDefaultSuccess(systemDictVo);
		}else{
			dictList = systemDictService.findByDictType(dictType);
			dto.setDefaultSuccess(dictList);
		}
		return dto;
	}
	
	
}