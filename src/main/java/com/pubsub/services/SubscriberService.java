package com.pubsub.services;

/***
 * Subscriberの処理サービスインタフェース
 * 
 * @author wtest
 * @version 1.0
 */
public interface SubscriberService {

	/**
	 * topic subscribe.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @param startTime subscribeの時刻.
	 * @return String.
	 */
	String topicSubscribe(String name, String topicName, long startTime);

	/**
	 * get message.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @return String.
	 */
	String messageGet(String name, String topicName);

	/**
	 * ack message.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @return String.
	 */
	String messageAck(String name, String topicName);

}
