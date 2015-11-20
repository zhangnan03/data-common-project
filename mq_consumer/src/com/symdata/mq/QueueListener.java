package com.symdata.mq;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements MessageListener {
	@Override
	public void onMessage(Message message) {
//		Student student = JSON.parseObject(message.getBody(), Student.class,
//				features);
		try {
			System.out.println("student_one:" + new String(message.getBody(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
