package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer01 {
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channle = RabbitMQUitl.getChannle();
        DefaultConsumer defaultConsumer = new DefaultConsumer(channle){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("reveive message :"+new String(body, StandardCharsets.UTF_8));
            }
        };

        channle.basicConsume(QUEUE, true,defaultConsumer);
    }
}
