package com.pubsub.services.impl;

import org.springframework.stereotype.Service;
import com.pubsub.services.SubscriberService;
import com.pubsub.util.FileUtil;

/***
 * Subscriberの処理サービス実装クラス
 * 
 * @author wtest
 * @version 1.0
 */
@Service
public class SubscriberServiceImpl implements SubscriberService {
	private static final String template_error = "Parameter error";
	private static final String template_subscribe = "Hello %s , %s successfully subscribed!";
	private static final String template_topic_not_exists = "Hello %s , %s not exists!";
	private static final String template_topic_not_subscribe = "Hello %s , %s No subscription!";
	private static final String template_topic_already_subscribe = "Hello %s , %s Already subscribed!";

	private static final String template_message = "%s!";
	private static final String template_ack = "Hello %s , %s Successfully ack!";
	private static final String template_ack_no = "Hello %s , %s no new message!";

	String basePath = System.getProperty("user.dir");
	String subscriberFilePath = basePath + "/data/subscriber/";
	String topicFilePath = basePath + "/data/topic/";

	/**
	 * topic subscribe.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @param startTime subscribeの時刻.
	 * @return String.
	 */
	@Override
	public String topicSubscribe(String userName, String topicName, long startTime) {
		if ("".equals(userName) || "".equals(topicName)) {
			return String.format(template_error);
		}

		// 主題ファイルすでに存在するかのチェック、存在しない場合、次の処理中断、エラーメッセージを返す
		String resultTopic = isTopicFileExists(userName, topicName);
		if (null != resultTopic) {
			return resultTopic;
		}

		String subscriberFile = subscriberFilePath + userName;
		// ユーザのsubscribe主題チェック、すでにsubscribe場合、次の処理中断、エラーメッセージを返す
		if (FileUtil.isExistsTopic(subscriberFile, topicName)) {
			return String.format(template_topic_already_subscribe, userName, topicName);
		}

		// 主題ファイル名
		String topicFile = topicFilePath + topicName;
		long topicFileCount = FileUtil.getFIleLineNum(topicFile);

		// subscriberファイルの新規(すでに存在したら、スキップする)
		FileUtil.createFile(subscriberFile);
		// subscriberファイルにの内容の追加
		FileUtil.writeFile(subscriberFile, topicName + ":" + startTime + ":" + ++topicFileCount);
		// 成功メッセージを返す
		return String.format(template_subscribe, userName, topicName);
	}

	/**
	 * get message.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @return String.
	 */
	@Override
	public String messageGet(String userName, String topicName) {
		if ("".equals(userName) || "".equals(topicName)) {
			return String.format(template_error);
		}

		// 主題ファイルすでに存在するかのチェック、存在しない場合、次の処理中断、エラーメッセージを返す
		String resultTopic = isTopicFileExists(userName, topicName);
		if (null != resultTopic) {
			return resultTopic;
		}
		String subscriberFile = subscriberFilePath + userName;
		// ユーザのsubscribe主題チェック、subscribeではない場合、次の処理中断、エラーメッセージを返す
		if (!FileUtil.isExistsTopic(subscriberFile, topicName)) {
			return String.format(template_topic_not_subscribe, userName, topicName);
		}

		String message = FileUtil.getMessageFromFile(subscriberFile, topicName, topicFilePath);

		return String.format(template_message, message);
	}

	/**
	 * ack message.
	 * 
	 * @param userName  Subscriberのユーザ名.
	 * @param topicName subscribeの主題名.
	 * @return String.
	 */
	@Override
	public String messageAck(String userName, String topicName) {
		if ("".equals(userName) || "".equals(topicName)) {
			return String.format(template_error);
		}

		// 主題ファイルすでに存在するかのチェック、存在しない場合、次の処理中断、エラーメッセージを返す
		String resultTopic = isTopicFileExists(userName, topicName);
		if (null != resultTopic) {
			return resultTopic;
		}

		String subscriberFile = subscriberFilePath + userName;
		// ユーザのsubscribe主題チェック、subscribeではない場合、次の処理中断、エラーメッセージを返す
		if (!FileUtil.isExistsTopic(subscriberFile, topicName)) {
			return String.format(template_topic_not_subscribe, userName, topicName);
		}

		boolean result = true;
		try {
			result = FileUtil.setSubscriberFileIndex(subscriberFile, topicName, topicFilePath);
		} catch (Exception e) {
			result = false;
		}
		if (!result) {
			return String.format(template_ack_no, userName, topicName);
		}
		return String.format(template_ack, userName, topicName);
	}

	private String isTopicFileExists(String userName, String topicName) {
		// 主題ファイル名
		String topicFile = topicFilePath + topicName;
		// 主題ファイルすでに存在するかのチェック、存在しない場合、次の処理中断、エラーメッセージを返す
		if (!FileUtil.isFileExists(topicFile)) {
			return String.format(template_topic_not_exists, userName, topicName);
		}
		return null;
	}

}
