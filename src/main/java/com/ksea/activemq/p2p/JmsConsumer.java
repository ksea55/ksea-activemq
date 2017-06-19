package com.ksea.activemq.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by ksea on 2017/6/1.
 * ActiveMQ消息消费者
 */
public class JmsConsumer {
    //连接ActiveMQ的用户名
    private static String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //连接ActiveMQ的密码
    private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //连接A窗体veMQ的URL地址
    private static String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {


        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageConsumer messageConsumer;

        try {
            //实例化连接工厂
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            //获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //获取会话session，这里的false表示不添加事务，
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //对消费者来说其目的地就是队列queue
            destination = session.createQueue("ksea");
            //创建消费者
            messageConsumer = session.createConsumer(destination);

            while (true) {
                TextMessage message = (TextMessage) messageConsumer.receive(100000);
                if (null != message) {
                    System.out.println("开始消费ActiveMQ的信息:" + message.getText());
                } else {
                    break;
                }

            }


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
