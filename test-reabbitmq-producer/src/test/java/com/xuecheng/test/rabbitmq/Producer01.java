package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    //队列名称
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channle = RabbitMQUitl.getChannle();
        channle.queueDeclare(QUEUE, true, false, false, null);
        String msg = "hello star";
        channle.basicPublish("", QUEUE, null, msg.getBytes());
        System.out.println("Sent Message is :"+msg);
    }

}
