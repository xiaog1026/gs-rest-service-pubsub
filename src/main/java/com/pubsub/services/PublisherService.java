package com.pubsub.services;

/***
 * Publisherの処理サービスインタフェース
 * 
 * @author wtest
 * @version 1.0
 */
public interface PublisherService {

	/**
	 * topic register.
	 * 
	 * @param userName  Publisherのユーザ名.
	 * @param topicName registerの主題名.
	 * @param startTime registerの時刻.
	 * @return String.
	 */
	String topicRegister(String userName, String topicName, long startTime);

	/**
	 * message publish.
	 * 
	 * @param userName  Publisherのユーザ名.
	 * @param topicName publishの主題名.
	 * @param startTime Publishの時刻.
	 * @param message   publishのメッセージ.
	 * @return String.
	 */
	String messagePublish(String userName, String topicName, long startTime, String message);

}
