/*
 * Copyright (c) 2018, 2025, Yue Chang and/or its affiliates. All rights reserved.
 * Yue Chang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.yc.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Yue Chang
 * @version 1.0
 * @className: Consumer
 * @description: 消息消费者
 * @date 2018年06月26日 11:49
 */
@Component
public class Consumer {

    // 消费者的组名
    @Value("${apache.rocketmq.consumer.PushConsumer}")
    private String consumerGroup;

    // NameServer地址
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @PostConstruct
    public void defaultMQPushConsumer() {

        // 消费者的组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);

        // 指定NameServer地址，多个地址以“;”分隔
        consumer.setNamesrvAddr(namesrvAddr);
        // 设置不适用VIP通道
        consumer.setVipChannelEnabled(false);


        try {
            // 订阅PushTopic下Tag为Push的消息
            consumer.subscribe("PushTopic", "push");

            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    try {
                        for (MessageExt messageExt : list) {
                            System.out.println("messageExt: " + messageExt); // 输出消息内容
                            String messsageBody = new String(messageExt.getBody(), "utf-8");
                            System.out.println("消费相应：Msg：" + messageExt.getMsgId() +
                                                ",msgBody:" + messsageBody);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        // 稍后再试
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    // 消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
