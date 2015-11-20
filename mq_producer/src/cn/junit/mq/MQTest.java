package cn.junit.mq;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.symdata.entity.Person;
import com.symdata.mq.MQTemplate;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
public class MQTest {
	@Autowired
	private MQTemplate mqTemplate;
	@Test
	public void testMQTest(){
		Person person = new Person();
		person.setName("уехЩ");
		
		for(int i=0;i<=100000;i++){
			person.setCard(i+"_one");
			mqTemplate.sendDataToQueue(person);
		}
	}
}
