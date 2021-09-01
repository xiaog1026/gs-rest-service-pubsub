Pub/Sub同様のミドルウエアサービスサーバー簡易システム、設計から実装までだいたい１０時間です。

test url:
1. Publisher topic register:  http://localhost:8080/topic/register?userName=pub_1&topicName=topic_1
1. Publisher message publish:  http://localhost:8080/message/publish?userName=pub_1&topicName=topic_1&message=message01
1. Subscriber topic subscribe:  http://localhost:8080/topic/subscribe?userName=sub1&topicName=topic_1
1. Subscriber message get:  http://localhost:8080/message/get?userName=sub1&topicName=topic_1
1. Subscriber message ack:  http://localhost:8080/message/ack?userName=sub1&topicName=topic_1
   

### 簡易設計図:

<img src="./image/pubsub.png" width="1000px">

### プログラム起動:

<img src="./image/springBootStart.png" width="400px">

### 画面打健テスト:
1. Publisher topic register: <img src="./image/test1.jpeg" width="700px">
1. Publisher message01 publish: <img src="./image/test2.jpeg" width="700px">
1. Publisher message02 publish: <img src="./image/test3.jpeg" width="700px">
1. Publisher message03 publish: <img src="./image/test4.jpeg" width="700px">
1. Subscriber topic subscribe: <img src="./image/test5.jpeg" width="700px">
1. Subscriber message get: <img src="./image/test6.jpeg" width="700px">
1. Publisher message04 publish: <img src="./image/test7.jpeg" width="700px">
1. Publisher message05 publish: <img src="./image/test8.jpeg" width="700px">
1. Subscriber message get: <img src="./image/test9.jpeg" width="700px">
1. Subscriber message ack: <img src="./image/test10.jpeg" width="700px">
1. Subscriber message get: <img src="./image/test11.jpeg" width="700px">
1. Subscriber message ack: <img src="./image/test12.jpeg" width="700px">
1. Subscriber message get: <img src="./image/test13.jpeg" width="700px">


