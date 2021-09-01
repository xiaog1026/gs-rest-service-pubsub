package com.pubsub.services.impl;

import com.pubsub.services.PublisherService;
import org.springframework.stereotype.Service;
import com.pubsub.util.FileUtil;

/***
 * Publisherの処理サービス実装クラス
 * 
 * @author wtest
 * @version 1.0
 */
@Service
public class PublisherServiceImpl implements PublisherService {
	private static final String template_error = "Parameter error";
	private static final String template_register = "Hello %s , %s successfully registered!";
	private static final String template_publish = "Hello %s , %s successfully publish!";
	private static final String template_topic_exists = "Hello %s , %s Already exists!";
	private static final String template_topic_not_exists = "Hello %s , %s not exists!";
	private static final String template_topic_not_register = "Hello %s , %s which owned by another publisher!";

	String basePath = System.getProperty("user.dir");
	String publisherFilePath = basePath + "/data/publisher/";
	String topicFilePath = basePath + "/data/topic/";

	/**
	 * topic register.
	 * 
	 * @param userName  Publisherのユーザ名.
	 * @param topicName registerの主題名.
	 * @param startTime registerの時刻.
	 * @return String.
	 */
	@Override
	public String topicRegister(String userName, String topicName, long startTime) {
		if ("".equals(userName) || "".equals(topicName)) {
			return String.format(template_error);
		}
		// publisherファイル名
		String publisherFile = publisherFilePath + userName;
		// 主題ファイル名
		String topicFile = topicFilePath + topicName;
		// 主題ファイルすでに存在するかのチェック、存在したら、次の処理中断、エラーメッセージを返す
		if (FileUtil.isFileExists(topicFile)) {
			return String.format(template_topic_exists, userName, topicName);
		}
		// publisherファイルの内容
		String contents = topicName + ":" + startTime;
		// publisherファイルの新規(すでに存在したら、スキップする)
		FileUtil.createFile(publisherFile);
		// publisherファイルにの内容の追加
		FileUtil.writeFile(publisherFile, contents);
		// 主題ファイルの新規
		FileUtil.createFile(topicFile);
		// 成功メッセージを返す
		return String.format(template_register, userName, topicName);
	}

	/**
	 * message publish.
	 * 
	 * @param userName  Publisherのユーザ名.
	 * @param topicName publishの主題名.
	 * @param startTime Publishの時刻.
	 * @param message   publishのメッセージ.
	 * @return String.
	 */
	@Override
	public String messagePublish(String userName, String topicName, long startTime, String message) {
		if ("".equals(userName) || "".equals(topicName) || "".equals(message)) {
			return String.format(template_error);
		}
		// publisherファイル名
		String publisherFile = publisherFilePath + userName;
		// 主題ファイル名
		String topicFile = topicFilePath + topicName;
		// 主題ファイルすでに存在するかのチェック、存在しない場合、次の処理中断、エラーメッセージを返す
		if (!FileUtil.isFileExists(topicFile)) {
			return String.format(template_topic_not_exists, userName, topicName);
		}
		// ユーザの公開主題チェック、自分の主題ではない場合、次の処理中断、エラーメッセージを返す
		if (!FileUtil.isExistsTopic(publisherFile, topicName)) {
			return String.format(template_topic_not_register, userName, topicName);
		}

		// 主題ファイルに内容の追加
		FileUtil.writeFile(topicFile, startTime + ":" + message);
		// 成功メッセージを返す
		return String.format(template_publish, userName, topicName);

	}

}
