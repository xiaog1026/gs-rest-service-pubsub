package com.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/***
 * Webアプリケーションプログラミングの入口クラス
 * 
 * @author wtest
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
