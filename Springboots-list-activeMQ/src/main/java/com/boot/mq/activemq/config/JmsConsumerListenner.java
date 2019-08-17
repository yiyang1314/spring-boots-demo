package com.boot.mq.activemq.config;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumerListenner {
    private Logger logger = LoggerFactory.getLogger(JmsConsumerListenner.class);

	    @JmsListener(destination = "${activemq.topic}", containerFactory = "firstTopicListener")
	    @Async // receive msg asynchronously
    //@Async("taskExecutePool") 
    public void receiveTopic(Message msg) throws JMSException {
        logger.debug(Thread.currentThread().getName() + ": topic===========" + msg.getStringProperty("username"));
        try {
            Thread.sleep(1000L);
            Map data=(Map)msg.getObjectProperty("data");
			System.out.println("已接受到消息："+data.get("username"));
			 logger.debug(Thread.currentThread().getName() + ": topic===========" + msg.getStringProperty("username"));
           msg.acknowledge();//确认收到消息
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug(Thread.currentThread().getName() + ": topic===========" + msg.getStringProperty("username"));
    }
    
	    @JmsListener(destination = "${activemq.queue}", containerFactory = "firstQueueListener")
	    @Async
    public void receiveQueue(Message msg) throws JMSException {
        logger.debug(Thread.currentThread().getName() + ": Queue===========" + msg.getStringProperty("username"));
        try {
            Thread.sleep(1000L);
            Map data=(Map)msg.getObjectProperty("data");
			System.out.println("已接受到消息："+data.get("username"));
			 logger.debug(Thread.currentThread().getName() + ": Queue===========" + msg.getStringProperty("username"));
           msg.acknowledge();//确认收到消息
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug(Thread.currentThread().getName() + ": Queue===========" + msg.getStringProperty("user"));
    }
    
    @JmsListener(destination = "${activemq.virtual.topic.A}", containerFactory = "firstQueueListener")
    @Async
    public void receiveVTopicA1(Message msg) throws JMSException {
        logger.debug(Thread.currentThread().getName() + ": vtopic A1===========" + msg.getStringProperty("username"));
        try {
            Thread.sleep(1000L);
            Map data=(Map)msg.getObjectProperty("data");
			System.out.println("已接受到消息vtopic："+data.get("username"));
			 logger.debug(Thread.currentThread().getName() + ": vtopic A2=1===========" + msg.getStringProperty("username"));
           msg.acknowledge();//确认收到消息
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug(Thread.currentThread().getName() + ": vtopic A1===========" + msg.getStringProperty("username"));
    }
    
    @JmsListener(destination = "${activemq.virtual.topic.A}", containerFactory = "firstQueueListener")
    @Async
    public void receiveVTopicA2(Message msg) throws JMSException {
        logger.debug(Thread.currentThread().getName() + ": vtopic A2===========" + msg.getStringProperty("username"));
        try {
            Thread.sleep(1000L);
            Map data=(Map)msg.getObjectProperty("data");
			System.out.println("已接受到消息 vtopic A2："+data.get("username"));
			 logger.debug(Thread.currentThread().getName() + ": vtopic A2===========" + msg.getStringProperty("username"));
           msg.acknowledge();//确认收到消息
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug(Thread.currentThread().getName() + ": vtopic A2===========" + msg.getStringProperty("username"));
    }
    
    @JmsListener(destination = "${activemq.virtual.topic.B}", containerFactory = "firstQueueListener")
    @Async
    public void receiveVTopicB(Message msg) throws JMSException {
        logger.debug(Thread.currentThread().getName() + ": vtopic B===========" + msg.getStringProperty("username"));
        try {
            Thread.sleep(1000L);
            Map data=(Map)msg.getObjectProperty("data");
			System.out.println("已接受到消息--vtopic B："+data.get("username"));
			 logger.debug(Thread.currentThread().getName() + ": Queue===========" + msg.getStringProperty("username"));
           msg.acknowledge();//确认收到消息
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug(Thread.currentThread().getName() + ": vtopic B===========" + msg.getStringProperty("username"));
    }
}