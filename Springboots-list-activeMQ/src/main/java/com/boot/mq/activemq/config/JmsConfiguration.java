package com.boot.mq.activemq.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.support.destination.DestinationResolver;
/**
 * JmsConfiguration： 配置模板和注入队列，订阅消息的监听
 * @author tangyang
 * @category 配置mq连接信息
 */
@Configuration
@EnableAsync // enable asynchronous task
@EnableJms
public class JmsConfiguration {
    	private Logger logger = LoggerFactory.getLogger(JmsConfiguration.class);
    
    	@Bean(name = "firstConnectionFactory")
	    public ActiveMQConnectionFactory getFirstConnectionFactory(@Value("${activemq.url}") String brokerUrl,
	            	@Value("${activemq.username}") String userName, @Value("${activemq.password}") String password){
		        logger.debug(brokerUrl + " - " + userName);
		          ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		        connectionFactory.setBrokerURL(brokerUrl);
		        connectionFactory.setUserName(userName);
		        connectionFactory.setPassword(password);
	        return connectionFactory;
	    }

    	@Bean(name = "firstJmsTemplate")
	    public JmsMessagingTemplate getFirstJmsTemplate(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
	        JmsMessagingTemplate template = new JmsMessagingTemplate(connectionFactory);
	        return template;
	    }
    
    	@Bean(name = "firstTopicListener")
	    public DefaultJmsListenerContainerFactory getFirstTopicListener(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
		        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		        factory.setConnectionFactory(connectionFactory);
		        factory.setPubSubDomain(true); // if topic, set true
		                // factory.setSessionAcknowledgeMode(4); // change acknowledge mode
	        return factory;
	    }

    	@Bean(name = "firstQueueListener")
	    public DefaultJmsListenerContainerFactory getFirstQueueListener(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory){
			        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
			        factory.setConnectionFactory(connectionFactory);
			               // factory.setSessionAcknowledgeMode(4); // change acknowledge mode
	        return factory;
	    }
}