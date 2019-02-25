package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerPublish02 {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_infrom_email";
    private static final String QUEUE_INFORM_SMS = "queue_infrom_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channle = RabbitMQUitl.getChannle();
        //声明交换机
        channle.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
        //声明队列
        channle.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
        channle.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
        //绑定交换机和队列
        channle.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_FANOUT_INFORM, "");
        channle.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");
        for (int i = 0; i < 10; i++) {
            String msg = "inform to user " + i;
            channle.basicPublish(EXCHANGE_FANOUT_INFORM, QUEUE_INFORM_EMAIL, null, msg.getBytes());
            System.out.println("Sent Message is :" + msg);
        }
    }

}
