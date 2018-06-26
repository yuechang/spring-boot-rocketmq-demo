### RocketMQ例子

#### Producer生产消息与Consumer消费消息代码编写
需要设置producer.setVipChannelEnabled(false);申明之后才是storeHost=/ip:10911，否则将是storeHost=/ip:10909。
有可能该端口是未启动的，如果未启动将会导致生产者消息不能正常发送，消费者消息也不能正常接收，也就需要使用10911端口来操作
错误日志如下：
``` java
Caused by: org.apache.rocketmq.remoting.exception.RemotingConnectException: connect to <10.0.4.127:10909> failed
	at org.apache.rocketmq.remoting.netty.NettyRemotingClient.invokeSync(NettyRemotingClient.java:359)
	at org.apache.rocketmq.client.impl.MQClientAPIImpl.sendMessageSync(MQClientAPIImpl.java:349)
	at org.apache.rocketmq.client.impl.MQClientAPIImpl.sendMessage(MQClientAPIImpl.java:333)
	at org.apache.rocketmq.client.impl.MQClientAPIImpl.sendMessage(MQClientAPIImpl.java:296)
	at org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl.sendKernelImpl(DefaultMQProducerImpl.java:693)
	at org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl.sendDefaultImpl(DefaultMQProducerImpl.java:460)
	... 29 more
```

#### RocketMQ监控服务
可以通过rocketmq-console-ng来图形化监控rocketMQ消息情况
 - 下载并maven编译
```java
git clone https://github.com/apache/rocketmq-externals.git
rocketmq-externals/rocketmq-console/
mvn clean package -Dmaven.test.skip=true
```

 - 启动监控服务，多个参数之间用空格隔开
```java
java -jar rocketmq-console-ng-1.0.0.jar --server.port=6666 --rocketmq.config.namesrvAddr=10.0.4.127:9876 --rocketmq.config.isVIPChannel=false
```

 - 访问监控服务

[http://127.0.0.1:6666](http://127.0.0.1:6666)
