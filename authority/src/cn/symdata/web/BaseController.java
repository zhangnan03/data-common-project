package cn.symdata.web;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import cn.symdata.common.Message;
import cn.symdata.template.directive.FlashMessageDirective;

public class BaseController {

	/** 错误视图 */
	protected static final String ERROR_VIEW = "/common/error";

	/**
	 * 添加瞬时消息
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes
	 * @param message
	 *            消息
	 */
	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
}