package cn.symdata.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.symdata.service.SystemDictService;
import cn.symdata.vo.SystemDictVo;

import com.google.common.collect.Lists;

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
@Component("systemDictDirective")
public class SystemDictDirective extends BaseDirective {
	/** 变量名称 */
	private static final String VARIABLE_NAME = "systemDicts";
	
	@Autowired
	private SystemDictService systemDictService;
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<SystemDictVo> dictList = Lists.newArrayList();
		SystemDictVo systemDictVo   = null;
		String dictType = this.getDictType(params);
		String dictCode = this.getDictCode(params);
		if(StringUtils.isNotBlank(dictType)&&StringUtils.isNotBlank(dictCode)){
			systemDictVo = systemDictService.findByDictTypeAndDictCode(dictType,dictCode);
			setLocalVariable(VARIABLE_NAME, systemDictVo, env, body);
		}else{
			dictList = systemDictService.findByDictType(dictType);
			setLocalVariable(VARIABLE_NAME, dictList, env, body);
		}
	}
}