package cn.symdata.template.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.symdata.common.utils.FreemarkerUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 模板指令 - 基类
 * 
 * 
 * @version 3.0
 */
public abstract class BaseDirective implements TemplateDirectiveModel {
	/** "ID"参数名称 */
	private static final String ID_PARAMETER_NAME = "id";
	/** "数量"参数名称 */
	private static final String COUNT_PARAMETER_NAME = "count";
	/**
	 * 字典类别
	 */
	private static final String DICT_TYPE="dictType";
	
	/**
	 * 字典代码
	 */
	private static final String DICT_CODE="dictCode"; 

	/**
	 * 获取ID
	 * 
	 * @param params
	 *            参数
	 * @return ID
	 */
	protected Long getId(Map<String, TemplateModel> params) throws TemplateModelException {
		return FreemarkerUtils.getParameter(ID_PARAMETER_NAME, Long.class, params);
	}

	/**
	 * 获取数量
	 * 
	 * @param params
	 *            参数
	 * @return 数量
	 */
	protected Integer getCount(Map<String, TemplateModel> params) throws TemplateModelException {
		return FreemarkerUtils.getParameter(COUNT_PARAMETER_NAME, Integer.class, params);
	}

	/**
	 * 设置局部变量
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            变量值
	 * @param env
	 *            Environment
	 * @param body
	 *            TemplateDirectiveBody
	 */
	protected void setLocalVariable(String name, Object value, Environment env, TemplateDirectiveBody body) throws TemplateException, IOException {
		TemplateModel sourceVariable = FreemarkerUtils.getVariable(name, env);
		FreemarkerUtils.setVariable(name, value, env);
		body.render(env.getOut());
		FreemarkerUtils.setVariable(name, sourceVariable, env);
	}

	/**
	 * 设置局部变量
	 * 
	 * @param variables
	 *            变量
	 * @param env
	 *            Environment
	 * @param body
	 *            TemplateDirectiveBody
	 */
	protected void setLocalVariables(Map<String, Object> variables, Environment env, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> sourceVariables = new HashMap<String, Object>();
		for (String name : variables.keySet()) {
			TemplateModel sourceVariable = FreemarkerUtils.getVariable(name, env);
			sourceVariables.put(name, sourceVariable);
		}
		FreemarkerUtils.setVariables(variables, env);
		body.render(env.getOut());
		FreemarkerUtils.setVariables(sourceVariables, env);
	}
	/**
	 * 
	 * @Title: getDictType 
	 * @Description: 获取字典类别
	 * @Autohr guoxuelian#zymdata.cn
	 * @param @param params
	 * @param @return
	 * @param @throws TemplateModelException   
	 * @return String  
	 * @throws 
	 * 2015年9月24日下午6:32:40
	 */
	protected String getDictType(Map<String, TemplateModel> params) throws TemplateModelException{
		return FreemarkerUtils.getParameter(DICT_TYPE, String.class, params);
	}
	
	/**
	 * 
	 * @Title: getDictCode 
	 * @Description: 获取字典代码
	 * @Autohr guoxuelian#zymdata.cn
	 * @param @param params
	 * @param @return
	 * @param @throws TemplateModelException   
	 * @return String  
	 * @throws 
	 * 2015年9月25日下午4:06:46
	 */
	protected String getDictCode(Map<String, TemplateModel> params) throws TemplateModelException{
		return FreemarkerUtils.getParameter(DICT_CODE, String.class, params);
	}

}