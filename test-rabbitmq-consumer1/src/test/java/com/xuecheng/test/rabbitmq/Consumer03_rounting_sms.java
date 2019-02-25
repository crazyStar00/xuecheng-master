package com.xuecheng.test.rabbitmq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer03_rounting_sms {

    //队列名称
    private static final String QUEUE_ROUNTING_INFORM_SMS = "queue_infrom_sms";
    private static final String EXCHANGE_DIRECT_INFORM = "exchange_direct_inform";

    public static void main(String[] args) throws IOException {
        Channel channle = RabbitMQUitl.getChannle();
        channle.queueBind(QUEUE_ROUNTING_INFORM_SMS, EXCHANGE_DIRECT_INFORM, QUEUE_ROUNTING_INFORM_SMS);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channle){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body, StandardCharsets.UTF_8));
            }
        };
        channle.basicConsume(QUEUE_ROUNTING_INFORM_SMS, true,defaultConsumer);
    }
}
