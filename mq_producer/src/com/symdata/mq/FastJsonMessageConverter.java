package com.symdata.mq;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.alibaba.fastjson.JSON;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:实现一个消息实体的转换格式
 *@Description:  
 *@Author:zhangnan#symdata
 *@Since:2015年11月2日  下午3:12:09
 *@Version:1.0
 */
public class FastJsonMessageConverter<T> extends AbstractMessageConverter {
	private static Logger logger = LoggerFactory.getLogger(FastJsonMessageConverter.class);

	public static final	String DEFAULT_CHARSET = "UTF-8";

    private volatile String defaultCharset = DEFAULT_CHARSET;

    public FastJsonMessageConverter() {
        super();
    }
    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null)?defaultCharset:DEFAULT_CHARSET;
    }
	@Override
	protected Message createMessage(Object objectToConvert, MessageProperties messageProperties) {
		byte[] bytes = null;
		try{
			String jsonString = JSON.toJSONString(objectToConvert);
            bytes = jsonString.getBytes(this.defaultCharset);
        }catch(UnsupportedEncodingException e) {
			throw new MessageConversionException("Failed to convert Message content",e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(this.defaultCharset);
        if(bytes != null){
            messageProperties.setContentLength(bytes.length);
        }
    	return new Message(bytes, messageProperties);
	}

	@SuppressWarnings("unchecked")
	public Object fromMessage(Message message) throws MessageConversionException {
		String json = "";
        try{
            json = new String(message.getBody(),defaultCharset);
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return (T) JSON.parseObject(json);
	}
}
