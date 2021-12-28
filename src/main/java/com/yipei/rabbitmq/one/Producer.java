package com.yipei.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author sunpei
 * @version 1.0
 * @date 2021/12/28
 */
public class Producer {
    //队列名称
    private static final String QUEUE_NAME = "Hello";

    //发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 1、队列名称
         * 2、队列里面的消息是否持久化(持久化指的是放到磁盘)，默认存储在内存中(false)
         * 3、该队列是否只供一个消费者进行消费 是否进行消息共享，true可以多个消费者消费 false只能一个消费者消费
         * 4、是否自动删除 最后一个消费者断开连接后 该队列是否自动删除 true自动删除 false不自动删除
         * 5、其他参数
         */
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //发送消息
        String message = "Hello World";

        /**
         *  1、发动到哪个交换机 本次定义为null
         *  2、路由的key值 本次是队列的名称
         *  3、其他参数信息
         *  4、发送消息的消息体(二进制)
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕");

    }
}
