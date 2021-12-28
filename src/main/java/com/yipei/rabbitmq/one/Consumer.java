package com.yipei.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author sunpei
 * @version 1.0
 * @date 2021/12/28
 */
public class Consumer {
    private static final String  QUEUE_NAME = "Hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明  接收消息
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println(new String(message.getBody()));//只拿到消息体
        };

        //取消的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        /**
         * 1、消费哪个队列
         * 2、消费成功之后是否需要自动应答  true自动 false 手动
         * 3、消费者未成功消费的回调
         * 4、消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

//        多个消费者接收消息时,工作的流程是一个接一个的接收消息(轮训),避免重复消费

        /**
         *rabbitmq的多个应用场景
         * 1、用户注册后，需要发注册邮件和注册短信(常规串行和并行---发送邮件和短信同步执行，三个任务都完成后返回客户端);注册后存入数据库然后发送给消息队列，再完成另两个任务
         * 2、降流、流量削峰
         */
    }
}
