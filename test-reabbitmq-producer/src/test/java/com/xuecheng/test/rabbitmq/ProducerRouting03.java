package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerRouting03 {
    //队列名称
    private static final String QUEUE_ROUTING_INFORM_EMAIL = "queue_infrom_email";
    private static final String QUEUE_ROUNTING_INFORM_SMS = "queue_infrom_sms";
    private static final String EXCHANGE_DIRECT_INFORM = "exchange_direct_inform";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channle = RabbitMQUitl.getChannle();
        //声明交换机
        channle.exchangeDeclare(EXCHANGE_DIRECT_INFORM, BuiltinExchangeType.DIRECT);
        //声明队列
        channle.queueDeclare(QUEUE_ROUTING_INFORM_EMAIL, true, false, false, null);
        channle.queueDeclare(QUEUE_ROUNTING_INFORM_SMS, true, false, false, null);
        //绑定交换机和队列
        channle.queueBind(QUEUE_ROUTING_INFORM_EMAIL, EXCHANGE_DIRECT_INFORM, QUEUE_ROUTING_INFORM_EMAIL);
        channle.queueBind(QUEUE_ROUNTING_INFORM_SMS, EXCHANGE_DIRECT_INFORM, QUEUE_ROUNTING_INFORM_SMS);
        for (int i = 0; i < 10; i++) {
            String msg = "email inform to user " + i;
            channle.basicPublish(EXCHANGE_DIRECT_INFORM, QUEUE_ROUTING_INFORM_EMAIL, null, msg.getBytes());
            System.out.println("Sent Message is :" + msg);
        }
        for (int i = 0; i < 10; i++) {
            String msg = "sms inform to user " + i;
            channle.basicPublish(EXCHANGE_DIRECT_INFORM, QUEUE_ROUNTING_INFORM_SMS, null, msg.getBytes());
            System.out.println("Sent Message is :" + msg);
        }
    }

}
