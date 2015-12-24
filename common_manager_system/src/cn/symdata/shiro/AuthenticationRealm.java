package cn.symdata.shiro;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.symdata.common.DataEnum.UserStatus;
import cn.symdata.common.exception.DatabaseException;
import cn.symdata.dao.UserDao;
import cn.symdata.entity.User;

import com.google.common.collect.Lists;
import com.octo.captcha.service.CaptchaService;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title: 用户身份及权限的验证
 *@Description:
 *@Author:zhangnan#symdata
 *@Since:2015年9月1日  下午3:20:53
 *@Version:1.0
 */
public class AuthenticationRealm extends AuthorizingRealm {

	@Resource(name = "imageCaptchaService")
	private CaptchaService captchaService;
	@Autowired
	private UserDao userDao;

	public AuthenticationRealm() {
		super();
		setCredentialsMatcher(new HashedCredentialsMatcher(
				Md5Hash.ALGORITHM_NAME));
	}
	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();
		if (!captchaService.validateResponseForID(captchaId, captcha.toUpperCase())) {
			throw new UnsupportedTokenException();
		}
		if (username != null && password != null) {
			User user;
			try {
				user = userDao.findByUsername(username);
			} catch (DatabaseException e) {
				throw new UnknownAccountException();
			}
			/** 没找到帐号 **/
			if (user == null) {
				throw new UnknownAccountException();
			}
			/** 账号被禁用 **/
			if (user.getStatus()==UserStatus.INVALID.getStatusCode()) {
				throw new DisabledAccountException();
			}
			if (user.getIsLocked()==UserStatus.VALID.getStatusCode()) {
				int loginFailureLockTime = 5;
				if (loginFailureLockTime == 0) {
					throw new LockedAccountException();
				}
				Date lockedDate = user.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
					user.setLoginFailureCount(0);
					user.setIsLocked(UserStatus.VALID.getStatusCode());
					user.setLockedDate(null);
//					try {
					userDao.save(user);
//					} catch (DatabaseException e) {
//						throw new LockedAccountException();
//					}
				} else {
					throw new LockedAccountException();
				}
			}
			if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
				int loginFailureCount = user.getLoginFailureCount() + 1;
				if (loginFailureCount >= 5) {
					user.setIsLocked(UserStatus.INVALID.getStatusCode());
					user.setLockedDate(new Date());
				}
				user.setLoginFailureCount(loginFailureCount);
				userDao.save(user);
			}
			user.setLoginIp(StringUtils.isNotEmpty(ip)?ip:"");
			user.setLoginDate(new Date());
			user.setLoginFailureCount(0);
			userDao.save(user);
			/** 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配 **/
			return new SimpleAuthenticationInfo(new Principal(user.getId(), user.getUsername()), user.getPassword(), getName());
		}
		throw new UnknownAccountException();
	}

	/**
	 * @Description:授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 * @Author:wangdezhen#zplay.cn
	 * @Since:2015年1月20日 上午10:14:18
	 * @Version:1.0
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authorities = Lists.newArrayList();
			try {
				User user = userDao.findByUsername(principal.getUsername());
				List<String> rolesList     = user.getRoles();      //查询该用户的角色
				List<String> powerList     = user.getPowers();     //查询该用户的按钮
				List<String> dataFieldList = user.getDataFields(); //查询该用户的字段
				authorities.addAll(rolesList);
				authorities.addAll(powerList);
				authorities.addAll(dataFieldList);
				if (authorities != null) {
					SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
					authorizationInfo.addStringPermissions(authorities);
					return authorizationInfo;
				}
			} catch (DatabaseException e) {
				throw new UnknownAccountException();
			}
		}
		return null;
	}
	/**
	 *@Description:清除用户授权信息缓存
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午2:45:53
	 *@Version:1.0
	 */
	  public void clearAllCachedAuthorizationInfo() {
		  Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		  if (cache != null) {
			  for (Object key : cache.keys()) { 
				  cache.remove(key); 
			  }
		  }
	}
	 

	/**
	 *@param principal
	 *@Description:更新用户授权信息缓存
	 *@Author:zhangnan#symdata
	 *@Since:2015年9月8日  下午2:46:02
	 *@Version:1.0
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
}