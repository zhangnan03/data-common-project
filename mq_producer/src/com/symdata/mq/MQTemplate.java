package com.symdata.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:
 *@Description:
 *@Author:zhangnan#symdata
 *@Since:2015年11月2日  下午3:12:45
 *@Version:1.0
 */
@Component
public class MQTemplate {
	 @Autowired
    private AmqpTemplate amqpTemplate;
    public void sendDataToQueue(Object obj) {
        amqpTemplate.convertAndSend("queue_one_key",obj);
    }  
}
