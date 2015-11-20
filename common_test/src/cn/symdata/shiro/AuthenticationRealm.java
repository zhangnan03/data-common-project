package cn.symdata.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.symdata.common.DataEnum.UserStatus;
import cn.symdata.entity.Admin;
import cn.symdata.service.AdminService;

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
	private AdminService adminService;

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
		if (!captchaService.validateResponseForID(captchaId, captcha.toUpperCase())) {
			throw new UnsupportedTokenException();
		}
		if (username != null && password != null) {
			Admin admin = adminService.findByUsername(username);
			/** 没找到帐号 **/
			if (admin == null) {
				throw new UnknownAccountException();
			}
			/** 账号被禁用 **/
			if (admin.getUserStatus()==UserStatus.INVALID) {
				throw new DisabledAccountException();
			}
			if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
				throw new IncorrectCredentialsException();
			}
			/** 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配 **/
			return new SimpleAuthenticationInfo(new Principal(admin.getId(), admin.getUsername()), admin.getPassword(), getName());
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
			List<String> authorities = adminService.findAuthorities(principal.getUsername());
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}
}