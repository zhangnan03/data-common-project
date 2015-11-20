package cn.symdata.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.DataEnum.ErrorCode;
import cn.symdata.common.Message;
import cn.symdata.common.SystemConfig;
import cn.symdata.common.exception.DatabaseException;
import cn.symdata.dao.DataFieldDao;
import cn.symdata.dao.PowerDao;
import cn.symdata.dao.RoleDao;
import cn.symdata.dao.UserDao;
import cn.symdata.dao.impl.UserDaoImpl;
import cn.symdata.entity.DataField;
import cn.symdata.entity.Power;
import cn.symdata.entity.Role;
import cn.symdata.entity.User;
import cn.symdata.service.UserService;
import cn.symdata.shiro.AuthenticationRealm;
import cn.symdata.shiro.Principal;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PowerDao powerDao;
	
	@Autowired
	private DataFieldDao dtaFieldDao;
	
	@Autowired
	private AuthenticationRealm authenticationRealm;
	@Autowired
	private SystemConfig systemConfig;
	
	@Override
	public Page<User> findUserByHql(User user,Integer page) throws DatabaseException {
		Pageable pageable = new PageRequest(page != null ? page.intValue() - 1 : 0, 10);
		return userDaoImpl.findUserByHql(user, pageable);
	}
	
	@Override
	public User findOne(String id) throws DatabaseException {
		return userDao.findOne(id);
	}
	
	@Override
	public User findByUsername(String username) throws DatabaseException {
		return userDao.findByUsername(username);
	}
	
	@Override
	public User findUserByUsernameAndPassword(String username, String password) throws DatabaseException {
		return userDao.findUserByUsernameAndPassword(username, password);
	}

	@Override
	public List<String> findAuthorities(String id) throws DatabaseException {
		List<String> authorities = new ArrayList<String>();
		User user = userDao.findOne(id);
		if (user != null) {
			authorities = user.getRoles();
		}
		return authorities;
	}
	
	@Override
	public User getCurrent() throws DatabaseException {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return userDao.findOne(principal.getId());
			}
		}
		return null;
	}
	
	@Override
	public String getCurrentUsername() throws DatabaseException {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}
	
	@Override
	public boolean isAuthenticated() throws DatabaseException {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.isAuthenticated();
		}
		return false;
	}
	
	@Override
	@Transactional(readOnly = false)
	public User save(User user) throws DatabaseException{
		user.setPassword(DigestUtils.md5Hex(systemConfig.getDefaultPassword()));
		return userDao.save(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message update(User user) throws DatabaseException{
		Message msg = new Message();
		if(!StringUtils.isNotBlank(user.getId())||!StringUtils.isNotBlank(user.getUsername())||!StringUtils.isNotBlank(user.getRealName())){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		User userParam = userDao.findOne(user.getId());
		if(userParam==null){
			msg.setError(ErrorCode.ERR1004.getCode(), ErrorCode.ERR1004.getDescription(), null);
			return msg;
		}
		userParam.setId(user.getId());
		userParam.setUsername(user.getUsername());
		userParam.setRealName(user.getRealName());
		userParam.setEmail(user.getEmail());
		userDao.save(userParam);
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message updateUserPassword(String newPwd,String oldPwd) throws DatabaseException {
		Message msg = new Message();
		if(!StringUtils.isNotBlank(newPwd)||!StringUtils.isNotBlank(oldPwd)){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		
		User user = this.getCurrent();
		if(user==null){
			msg.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return msg;
		}
		if(!DigestUtils.md5Hex(oldPwd).equals(user.getPassword())){
			msg.setError(ErrorCode.ERR1003.getCode(), ErrorCode.ERR1003.getDescription(), null);
			return msg;
		}
		userDao.updateUserPassword(DigestUtils.md5Hex(newPwd), user.getUsername());
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message updateUserIsEnable(String id) throws DatabaseException {
		Message msg = new Message();
		if(!StringUtils.isNotBlank(id)){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		userDao.updateUserIsEnable(id);
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message updateUsernameAndRealNameAndEmailAndIsEnable(User user) throws DatabaseException {
		Message msg = new Message();
		if(user==null||!StringUtils.isNotBlank(user.getRealName())||!StringUtils.isNotBlank(user.getId())){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		userDao.updateUsernameAndRealNameAndEmailAndIsEnable(user.getUsername(), user.getRealName(), user.getEmail(), user.getStatus(), user.getId());
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message authorizeRoleOperate(String userId,String roleIds) throws DatabaseException {
		Message msg         = new Message();
		Role role           = null;
		List<Role> roleList = Lists.newArrayList();
		
		if(!StringUtils.isNotBlank(userId)){//参数不正确
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		User user = this.findOne(userId);
		if(user==null){//用户不存在
			msg.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return msg;
		}
		if(StringUtils.isNotBlank(roleIds)){
			List<String> list = Splitter.on(",").trimResults().splitToList(roleIds.substring(1));
			for (String string : list) {
				if(StringUtils.isNotBlank(string)){
					role = roleDao.findOne(string);
					if(role!=null) roleList.add(role);
				}
			}
		}
		user.setRoleList(roleList);
		userDao.save(user);
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message authorizePowerOperate(String userId,String powerIds) throws DatabaseException {
		Message msg           = new Message();
		Power power           = null;
		List<Power> powerList = Lists.newArrayList();
		
		if(!StringUtils.isNotBlank(userId)){//参数不正确
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		User user = this.findOne(userId);
		if(user==null){//用户不存在
			msg.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return msg;
		}
		if(StringUtils.isNotBlank(powerIds)){
			List<String> list = Splitter.on(",").trimResults().splitToList(powerIds.substring(1));
			for (String string : list) {
				if(StringUtils.isNotBlank(string)){
					power = powerDao.findOne(string);
					if(power!=null) powerList.add(power);
				}
			}
		}
		user.setPowerList(powerList);
		userDao.save(user);
		msg.setDefaultSuccess(null);
		return msg;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Message authorizeDataFieldOperate(String userId,String dataFieldIds) throws DatabaseException {
		List<DataField> dataFieldList = Lists.newArrayList();
		Message msg                   = new Message();
		DataField dataField           = null;
		
		if(!StringUtils.isNotBlank(userId)){//参数不正确
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		
		User user = this.findOne(userId);
		if(user==null){//用户不存在
			msg.setError(ErrorCode.ERR1002.getCode(), ErrorCode.ERR1002.getDescription(), null);
			return msg;
		}
		if(StringUtils.isNotBlank(dataFieldIds)){
			List<String> list = Splitter.on(",").trimResults().splitToList(dataFieldIds.substring(1));
			for (String string : list) {
				if(StringUtils.isNotBlank(string)){
					dataField = dtaFieldDao.findOne(string);
					if(dataField!=null) dataFieldList.add(dataField);
				}
			}
		}
		user.setDataFieldList(dataFieldList);
		userDao.save(user);
		msg.setDefaultSuccess(null);
		return msg;
	}

	@Override
	public boolean findByUserNameAndId(String username, String id) throws DatabaseException {
		User user = null;
		username  = StringUtils.trimToEmpty(username);
		if(Strings.isNullOrEmpty(username)){
			return false;
		}
		if(StringUtils.isNotBlank(id)){
			user = userDao.findByUserNameAndId(username,id);
		}else{
			user = userDao.findByUsername(username);
		}
		if(user!=null){
			return false;
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public Message updateStatus(int status,String id) throws DatabaseException {
		Message msg = new Message();
		if(!StringUtils.isNotBlank(id)){
			msg.setError(ErrorCode.ERR1001.getCode(), ErrorCode.ERR1001.getDescription(), null);
			return msg;
		}
		userDao.updateStatus(status,id);
		msg.setDefaultSuccess(null);
		return msg;
	}
}
