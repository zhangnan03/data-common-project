package cn.symdata.common;

import java.io.Serializable;

import cn.symdata.common.DataEnum.ErrorCode;
import cn.symdata.common.utils.JsonMapper;

/**
 * 
 * @ClassName: Message 
 * @Description: 消息
 * @author guoxuelian@symdata.cn
 * @date 2015年9月16日 下午6:39:21 
 *
 */
public class Message implements Serializable{

	private static final long serialVersionUID = -2490301309324385398L;
	private String code;				//状态码
	private String message;				//消息
	private Object data;				//Json
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setError(String code,String message,Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public void setDefaultSuccess(Object data) {
		setError(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getDescription(),data);
	}
	public void setDefaultError(Object data) {
		setError(ErrorCode.ERR9999.getCode(), ErrorCode.ERR9999.getDescription(),data);
	}
	
	@Override
	public String toString() {
		JsonMapper binder = JsonMapper.nonDefaultMapper();
		return binder.toJson(this);
	}
}