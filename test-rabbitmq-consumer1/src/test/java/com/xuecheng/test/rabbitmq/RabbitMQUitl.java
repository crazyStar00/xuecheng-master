package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUitl {
    public static int retryNum = 0;
    public static int retryMax = 3;
    public static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("tencent");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //rabbitmq默认虚拟机名称为"/"，虚拟机相当于一个独立的mq服务
        connectionFactory.setVirtualHost("/");
    }

    public static Channel getChannle() {
        Channel channel = null;
        try {
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        } catch (Exception e) {
            retryNum += 1;
            if(retryNum > retryMax) {
                throw new RuntimeException(e.getMessage());
            }
            getChannle();
        }
        return channel;
    }
}
