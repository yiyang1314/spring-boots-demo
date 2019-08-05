package com.boot.mq.uitls;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ATMQUilts {
	private Session session=null;
	private Connection conn=null;
	public void init() {
		try {
			//创建连接工厂,三个参数分别是用户名、密码以及消息队列所在地址
		    ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory(
		            "admin",//ActiveMQConnection.DEFAULT_USER,
		            "admin",//ActiveMQConnection.DEFAULT_PASSWORD,
		            "tcp://localhost:61616");
		
		    //连接到JMS提供者
		    Connection conn = connFactory.createConnection();
		    //开启连接
		    conn.start();
		
		    //事务性会话，自动确认消息
		    session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
		}catch (Exception e){
		    e.printStackTrace();
		
		}

	}
	public  void send() 
	{
		  try {

			    //消息的目的地，创建队列"queue"
			    Destination destination = session.createQueue("queue");
			    //消息生产者
			    MessageProducer producer = session.createProducer(destination);
			
			//   //文本消息
			//  TextMessage textMessage = session.createTextMessage("这是文本消息");
			//  producer.send(textMessage);
			
			    //键值对消息
			    MapMessage mapMessage = session.createMapMessage();
			    //将消息内容放入到消息里
			    mapMessage.setString("reqDesc", "您好，美女！");
			    //生产者传送消息
			    producer.send(mapMessage);
			//
			//    //流消息
			//    StreamMessage streamMessage = session.createStreamMessage();
			//    streamMessage.writeString("这是流消息");
			//    producer.send(streamMessage);
			//
			//    //字节消息
			//    String s = "BytesMessage字节消息";
			//    BytesMessage bytesMessage = session.createBytesMessage();
			//    bytesMessage.writeBytes(s.getBytes());
			//    producer.send(bytesMessage);
			//
			//    //对象消息
			//    User user = new User("obj_info", "对象消息"); //User对象必须实现Serializable接口
			//    ObjectMessage objectMessage = session.createObjectMessage();
			//    objectMessage.setObject(user);
			//    producer.send(objectMessage);
			
			
			    session.commit(); //提交会话，该条消息会进入"queue"队列，生产者也完成了历史使命
			    producer.close();
			    session.close();
			    conn.close();
			
			}catch (Exception e){
			    e.printStackTrace();
			
			}

	}
	
	public void get(){
	try {
        Destination dest = session.createQueue("queue");
 
        MessageConsumer messConsumer = session.createConsumer(dest);
        //方式1设置消息监听
        messConsumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {
                try {
                    MapMessage m = (MapMessage)message;
                    System.out.println("consumer接收到"+m.getString("reqDesc")+"的请求并开始处理，时间是"+new Date());
                    System.out.println("这里会停顿5s，模拟系统处理请求，时间是"+new Date());
                    Thread.sleep(5000);
                    System.out.println("consumer接收到"+m.getString("reqDesc")+"的请求并处理完毕，时间是"+new Date());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
      //方式2主动接收消息
//        while(true){
//            try {
//                MapMessage m = (MapMessage) messConsumer.receive();
//              
//                Thread.sleep(1000);
//                System.out.println(m.getString("reqDesc"));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
 
//        if(conn != null)conn.close();
		      }catch (Exception e){
		      e.printStackTrace();
		  }
    }

	
	
	public static void main(String[] args) {
		  
	}

//原文链接：https://blog.csdn.net/superyu1992/article/details/82380880
}
