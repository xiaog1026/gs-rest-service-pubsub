package com.pubsub.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

/***
 * File を扱うユーティリティ・クラス
 * 
 * @author wtest
 * @version 1.0
 */
public class FileUtil {
	public static void writeFile(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(conent + System.getProperty("line.separator"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isFileExists(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	public static long getFIleLineNum(String fileName) {
		try {
			return Files.lines(Paths.get(fileName)).count();
		} catch (IOException e) {
			return -1;
		}
	}

	public static boolean createFile(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static boolean isExistsTopic(String fileName, String topicName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text;
			while ((text = br.readLine()) != null) {
				if (text.split(":")[0].equals(topicName)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getMessageFromFile(String fileName, String topicName, String topicPath) {
		long startIndex = 0;
		String message = "no new message";
		try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
			String text;
			while ((text = br.readLine()) != null) {
				if (text.split(":")[0].equals(topicName)) {
					startIndex = Long.valueOf(text.split(":")[2]);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		File topicFile = new File(topicPath + topicName);
		try (BufferedReader br = new BufferedReader(new FileReader(topicFile))) {
			String text;
			long count = 1;
			while ((text = br.readLine()) != null) {
				if (startIndex == count) {
					message = text.split(":")[1];
					break;
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	public static boolean setSubscriberFileIndex(String fileName, String topicName, String topicPath) throws Exception {
		boolean result = true;
		long topicFileCount = getFIleLineNum(topicPath + topicName);
		long startIndex = 0;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			String[] strs = line.split(":");
			if (strs[0].equals(topicName)) {
				startIndex = Long.valueOf(strs[2]);
				startIndex++;
				if ((++topicFileCount) < startIndex) {
					result = false;
					break;
				} else {
					sb.append(strs[0]).append(":").append(strs[1]).append(":").append(startIndex);
				}
			} else {
				sb.append(line);
			}
			sb.append(System.getProperty("line.separator"));
		}
		reader.close();

		if (result) {
			RandomAccessFile mm = new RandomAccessFile(fileName, "rw");
			mm.writeBytes(sb.toString());
			mm.close();
		}

		return result;
	}
}
