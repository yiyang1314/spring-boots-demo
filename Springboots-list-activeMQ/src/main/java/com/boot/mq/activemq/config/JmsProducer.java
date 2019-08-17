package com.boot.mq.activemq.config;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import springfox.documentation.spring.web.json.Json;

@Component
public class JmsProducer {
    private Logger logger = LoggerFactory.getLogger(JmsProducer.class);
	    @Autowired
	    @Qualifier("firstJmsTemplate")
	    private JmsMessagingTemplate jmsTemplate;
	    @Autowired   
	    private  ActiveMQQueue mqQueue;
	     @Autowired
	    private ActiveMQTopic mqTopic;
	    @Autowired
	    @Qualifier("activeMQMessage")
	    private  ActiveMQMessage msg;
	    
    public void sendMsg(Destination destination, Message msg) {
        jmsTemplate.convertAndSend(destination, msg);
        logger.debug("发送成功");
    }

    /**
     * send msg to queue.
     * @param data
     */
    public void sendToQueue(Map<String, String> data) {
    	  logger.debug("正在发送队列消息中.....");
        try {
        	System.out.println("已接受到消息："+data);
        	 msg.setObjectProperty("data", data);
            msg.setStringProperty("username", data.get("username"));
            msg.setStringProperty("password", data.get("password"));
            msg.setStringProperty("user", data.get("user"));
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sendMsg(mqQueue, msg);
    	System.out.println("已发送到消息："+data);
    }

    /**
     * send msg to topic.
     * @param data
     */
    public void sendToTopic(Map<String, String> data) {
    	logger.debug("正在发布订阅消息中.....");
        try {
            msg.setStringProperty("username", data.get("username"));
            msg.setStringProperty("password", data.get("password"));
            msg.setStringProperty("user", data.get("user"));
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sendMsg(mqTopic, msg);
    }

    /**
     * send msg to virtual topic.
     * @param data
     */
    public void sendToVTopic(Map<String, String> data) {

        try {
            msg.setStringProperty("value", data.get("value"));
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sendMsg(mqTopic, msg);
        System.out.println("已发送到消息："+data);
    }
}