package com.boot.mq;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.mq.activemq.config.JmsConsumer;
import com.boot.mq.activemq.config.JmsProducer;
import com.boot.mq.activemq.config.TopicJmsPDCU;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class ActivemqTest {
	@Autowired
	private JmsConsumer jmsConsumer; 
	@Autowired
	private JmsProducer jmsProducer;
	
	@Autowired
	private TopicJmsPDCU topicJmsPDCU;
	 @Test
	    public void sendMsg() {
	        for (int i = 0; i < 10; i++) {
	            Map<String, String> map = new HashMap<String, String>();
	            map.put("value", "value = " + i);
	           // jmsProducer.sendToTopic(map);
	            jmsProducer.sendToQueue(map);
	            //jmsProducer.sendToVTopic(map);
	        }
	 }

}
