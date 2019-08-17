package com.boot.mq.activemq.config;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;

@Configuration
public class ActiveMqConfiguration {
    private Logger logger = LoggerFactory.getLogger(ActiveMqConfiguration.class);
//		    @Autowired
//		    @Qualifier("firstJmsTemplate")
//		    private JmsMessagingTemplate jmsTemplate;
		    
		    @Value("${activemq.topic}")
		    private String topic;
		    
		    @Value("${activemq.queue}")
		    private String queue;
		
		    @Value("${activemq.virtual.topic}")
		    private String vTopic;
	
		 @Bean
		 public ActiveMQQueue mqQueue() {
			 	logger.debug("初始化队列："+queue);
		    	return new ActiveMQQueue(queue);
		    }   
		 @Bean
		 public ActiveMQTopic mqTopic() {
			 logger.debug("初始化订阅队列："+topic);
		    	return new ActiveMQTopic(topic);
		    }   
		 @Bean
		 public ActiveMQMessage activeMQMessage() {
			 	logger.debug("初始化消息对象");
		    	return new ActiveMQMessage();
		    }
	
}
