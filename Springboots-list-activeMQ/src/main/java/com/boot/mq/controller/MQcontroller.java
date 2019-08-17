package com.boot.mq.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.boot.mq.activemq.config.JmsConsumerListenner;
import com.boot.mq.activemq.config.JmsProducer;
import com.boot.mq.activemq.config.TopicJmsPDCU;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@EnableAutoConfiguration
@Api(value="API" ,description="在线测试API文档")
@RestController
@RequestMapping("/mq/API")
@EnableAsync
public class MQcontroller {
	
	@Autowired
    	@Qualifier("jmsConsumerListenner")
	private JmsConsumerListenner jmsConsumer; 
	@Autowired
	private JmsProducer jmsProducer;
	
	

	@ApiOperation(value="发送队列" ,tags="发送消息")
	@RequestMapping(value="queue",method=RequestMethod.GET)
	public Map<String, String> login(String name,String password) {
		Map<String, String> data=new HashMap<String, String>();
		data.put("username", name);
		data.put("password", password);
		data.put("login", "user");
		System.out.println("发送队列消息开始---------------------------------------");
		System.out.println("发送队列消息："+data);
		jmsProducer.sendToQueue(data);
		System.out.println("发送队列消息完毕---------------------------------------");
		return data;
	}
	
	@ApiOperation(value="发布订阅API接口" ,tags="sendToQueue")
	@RequestMapping(value="topic",method=RequestMethod.GET)
	public Map<String, String> del(String name,String password) {
		Map<String, String> data=new HashMap<String, String>();
		data.put("username", name);
		data.put("password", password);
		data.put("login", "user");
		System.out.println("sendToQueue发送队列消息开始---------------------------------------");
		System.out.println("sendToQueue发送队列消息："+data);
		jmsProducer.sendToTopic(data);
		System.out.println("sendToQueue发送队列消息完毕---------------------------------------");
		return data;
	}
	
	
	@ApiOperation(value="虚拟机发布订阅API接口" ,tags="sendToVTopic")
	@RequestMapping(value="VMtopic",method=RequestMethod.GET)
	public Map<String, String> save(String name,String password) {
		Map<String, String> data=new HashMap<String, String>();
		data.put("username", name);
		data.put("password", password);
		data.put("login", "user");
		System.out.println("sendToVTopic发送队列消息开始---------------------------------------");
		System.out.println("sendToVTopic发送队列消息："+data);
		jmsProducer.sendToVTopic(data);;
		System.out.println("sendToVTopic发送队列消息完毕---------------------------------------");
		return data;
	}
}
