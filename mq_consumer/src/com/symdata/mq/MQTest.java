package com.symdata.mq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class MQTest {

	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("192.168.2.221");
//      factory.setUsername("test_user");
//      factory.setPassword("user123");
	    factory.setUsername("guest");
	    factory.setPassword("guest");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    channel.queueDeclare("QUEUE_NAME", false, false, false, null);//��һ��Queue�Ƿ����
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume("QUEUE_NAME", true, consumer);
	    while (true) {
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();//������ֱ�����յ�һ����Ϣ
	      String message = new String(delivery.getBody());
	      System.out.println(" [x] Received '" + message + "'");
	    }

	}

}
