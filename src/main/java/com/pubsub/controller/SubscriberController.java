package com.pubsub.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pubsub.bean.Message;
import com.pubsub.services.SubscriberService;

/***
 * Subscriberの処理コントローラークラス
 * 
 * @author wtest
 * @version 1.0
 */
@RestController
public class SubscriberController {

	@Autowired
	SubscriberService subscriberService;

	private static final String template_time = "Time, %d ms";

	private final AtomicLong counter = new AtomicLong();

	/**
	 * topic subscribe.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @return Message.
	 */
	@GetMapping("/topic/subscribe")
	public Message topicSubscribe(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "topicName") String topicName) {
		long startTime = System.currentTimeMillis();
		String resultMessage = subscriberService.topicSubscribe(userName, topicName, startTime);
		long endTime = System.currentTimeMillis();
		return new Message(counter.incrementAndGet(), String.format(template_time, endTime - startTime), resultMessage);
	}

	/**
	 * get message.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @return Message.
	 */
	@GetMapping("/message/get")
	public Message messageGet(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "topicName") String topicName) {
		long startTime = System.currentTimeMillis();
		String resultMessage = subscriberService.messageGet(userName, topicName);
		long endTime = System.currentTimeMillis();
		return new Message(counter.incrementAndGet(), String.format(template_time, endTime - startTime), resultMessage);
	}

	/**
	 * ack message.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @return Message.
	 */
	@GetMapping("/message/ack")
	public Message messageAck(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "topicName") String topicName) {
		long startTime = System.currentTimeMillis();
		String resultMessage = subscriberService.messageAck(userName, topicName);
		long endTime = System.currentTimeMillis();
		return new Message(counter.incrementAndGet(), String.format(template_time, endTime - startTime), resultMessage);
	}

}
