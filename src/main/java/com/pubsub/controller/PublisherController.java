package com.pubsub.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pubsub.bean.Message;
import com.pubsub.services.PublisherService;

/***
 * Publisherの処理コントローラークラス
 * 
 * @author wtest
 * @version 1.0
 */
@RestController
public class PublisherController {

	@Autowired
	PublisherService publisherService;

	private static final String template_time = "Time, %d ms";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * topic register.
	 * 
	 * @param userName  Publisherのユーザ名.
	 * @param topicName registerの主題名.
	 * @return Message.
	 */
	@GetMapping("/topic/register")
	public Message topicRegister(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "topicName") String topicName) {

		long startTime = System.currentTimeMillis();
		String resultMessage = publisherService.topicRegister(userName, topicName, startTime);
		long endTime = System.currentTimeMillis();
		return new Message(counter.incrementAndGet(), String.format(template_time, endTime - startTime), resultMessage);
	}

	/**
	 * message publish.
	 * 
	 * @param userName  Publisherのユーザ名.
	 * @param topicName publishの主題名.
	 * @param message   publishのメッセージ.
	 * @return Message.
	 */
	@GetMapping("/message/publish")
	public Message messagePublish(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "topicName") String topicName, @RequestParam(value = "message") String message) {
		long startTime = System.currentTimeMillis();
		String resultMessage = publisherService.messagePublish(userName, topicName, startTime, message);
		long endTime = System.currentTimeMillis();
		return new Message(counter.incrementAndGet(), String.format(template_time, endTime - startTime), resultMessage);
	}

}
