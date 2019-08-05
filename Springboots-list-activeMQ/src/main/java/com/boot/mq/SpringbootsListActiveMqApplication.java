package com.boot.mq;

import java.util.Date;

import javax.jms.*;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.boot.mq.annotation.Logger;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@ComponentScan("com.boot.mq.*")
public class SpringbootsListActiveMqApplication {
	
	public static void main(String[] args) {
		log.debug("welcome here !");
		SpringApplication.run(SpringbootsListActiveMqApplication.class, args);
	}

//	public  void init() throws JMSException {
//        //前期的初始化工作与生产者相同
//        ConnectionFactory factory = new ActiveMQConnectionFactory(
//                ActiveMQConnectionFactory.DEFAULT_USER,
//                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
//                "tcp://localhost:61616"
//        );
// 
//        Connection conn = factory.createConnection();
//        conn.start();
// 
//        Session session = conn.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
//        //与生产者的消息目的地相同
//        Destination dest = session.createQueue("queue");
// 
//        MessageConsumer messConsumer = session.createConsumer(dest);
//        //方式1设置消息监听
//        messConsumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                try {
//                    MapMessage m = (MapMessage)message;
//                    System.out.println("consumer接收到"+m.getString("reqDesc")+"的请求并开始处理，时间是"+new Date());
//                    System.out.println("这里会停顿5s，模拟系统处理请求，时间是"+new Date());
//                    Thread.sleep(5000);
//                    System.out.println("consumer接收到"+m.getString("reqDesc")+"的请求并处理完毕，时间是"+new Date());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//      //方式2主动接收消息
////        while(true){
////            try {
////                MapMessage m = (MapMessage) messConsumer.receive();
////              
////                Thread.sleep(1000);
////                System.out.println(m.getString("reqDesc"));
////            }catch (Exception e){
////                e.printStackTrace();
////            }
////
////        }
// 
////        if(conn != null)conn.close();
//    }

}
