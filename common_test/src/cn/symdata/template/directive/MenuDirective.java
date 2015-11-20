package cn.symdata.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.symdata.common.DataEnum.ErrorCode;
import cn.symdata.common.SystemConfig;
import cn.symdata.common.utils.JsonMapper;
import cn.symdata.common.utils.RemoteContent;
import cn.symdata.dto.TransDto;
import cn.symdata.shiro.Principal;
import cn.symdata.vo.MenuVo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:
 *@Description:菜单指令
 *@Author:zhangnan#symdata
 *@Since:2015年9月15日  下午7:37:02
 *@Version:1.0
 */
@Component("menuDirective")
public class MenuDirective extends BaseDirective {
	/** 变量名称 */
	private static final String VARIABLE_NAME = "menus";
	
	@Autowired
	private SystemConfig config;
	
	private final JsonMapper binder = JsonMapper.nonDefaultMapper();

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@SuppressWarnings({"rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<MenuVo> menus = Lists.newArrayList();
		String username =null;
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				username = principal.getUsername();
			}
		}
		if(username!=null){
			Map<String,String> paramsMap = Maps.newConcurrentMap();
			paramsMap.put("username", username);
			paramsMap.put("systemId", config.getSystemId());
			String result = RemoteContent.sendPostByParams(paramsMap, config.getCheckUserServerName()+"/remote/menu.jhtml");
			TransDto dto = binder.fromJson(result, TransDto.class);
			if (dto!=null&&dto.getCode().equals(ErrorCode.SUCCESS.getCode()) && dto.getData() != null) {
				menus = binder.fromJson(binder.toJson(dto.getData()),binder.contructCollectionType(List.class, MenuVo.class));
			}
		}
		setLocalVariable(VARIABLE_NAME, menus, env, body);
	}
}