package com.symdata.mq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MQTest {

	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("192.168.2.221");  
//        factory.setUsername("test_user");
//        factory.setPassword("user123");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
  
        channel.queueDeclare("QUEUE_NAME", false, false, false, null);  
        String message = "Xue Lian Hello World!";  
        channel.basicPublish("", "QUEUE_NAME", null, message.getBytes());  
        System.out.println(" [x] Sent '" + message + "'");  
        channel.close();  
        connection.close();  

	}

}
