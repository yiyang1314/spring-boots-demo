package com.boot.mq.activemq.config;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
@Component
public class TopicJmsPDCU {
    private Logger logger = LoggerFactory.getLogger(TopicJmsPDCU.class);
    @Autowired
    @Qualifier("firstJmsTemplate")
    private JmsMessagingTemplate jmsTemplate;
    
    @Value("${activemq.topic}")
    private String topic;
    
    @Value("${activemq.queue}")
    private String queue;

     @Value("${activemq.virtual.topic}")
    private String vTopic;
    
    public void sendMsg(Destination destination, Message msg) {
        jmsTemplate.convertAndSend(destination, msg);
    }

	// 消息发送
	/**
	 * send msg to virtual topic.
	 * @param data
	 */
	public void sendToVTopic(Map<String, String> data) {
	    ActiveMQTopic mqVTopic = new ActiveMQTopic(vTopic);
	    ActiveMQMessage msg = new ActiveMQMessage();
	    try {
	        msg.setStringProperty("value", data.get("value"));
	    } catch (JMSException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    sendMsg(mqVTopic, msg);
	}

	// 消息监听
	@JmsListener(destination = "${activemq.virtual.topic.A}", containerFactory = "firstQueueListener")
	@Async
	public void receiveVTopicA1(Message msg) throws JMSException {
	    logger.debug(Thread.currentThread().getName() + ": vtopic A1===========" + msg.getStringProperty("value"));
	    try {
	        Thread.sleep(500L);
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    // logger.debug(Thread.currentThread().getName() + ": vtopic A1===========" + msg.getStringProperty("value"));
	}

	@JmsListener(destination = "${activemq.virtual.topic.A}", containerFactory = "firstQueueListener")
	@Async
	public void receiveVTopicA2(Message msg) throws JMSException {
	    logger.debug(Thread.currentThread().getName() + ": vtopic A2===========" + msg.getStringProperty("value"));
	    try {
	        Thread.sleep(500L);
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    // logger.debug(Thread.currentThread().getName() + ": vtopic A2===========" + msg.getStringProperty("value"));
	}

	@JmsListener(destination = "${activemq.virtual.topic.B}", containerFactory = "firstQueueListener")
	@Async
	public void receiveVTopicB(Message msg) throws JMSException {
	    logger.debug(Thread.currentThread().getName() + ": vtopic B===========" + msg.getStringProperty("value"));
	    try {
	        Thread.sleep(500L);
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    // logger.debug(Thread.currentThread().getName() + ": vtopic B===========" + msg.getStringProperty("value"));
	}
}
