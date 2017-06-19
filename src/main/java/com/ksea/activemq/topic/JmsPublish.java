package com.ksea.activemq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by ksea on 2017/6/19.
 * 消息发布者-也就是消息生产者
 */
public class JmsPublish {
    //连接ActiveMQ的用户名
    private static String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //连接ActiveMQ的密码
    private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //连接ActiveMQ的URL地址
    private static String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {

        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageProducer messageProducer;

        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            connection = connectionFactory.createConnection();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            destination = session.createTopic("ksea_Topic");
            messageProducer = session.createProducer(destination);

            sendMessage(session, messageProducer);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }

    private static void sendMessage(Session session, MessageProducer messageProducer) {

        for (int i = 1; i <= 10; i++) {
            try {
                TextMessage textMessage = session.createTextMessage("Sublish发了信息:" + i);
                messageProducer.send(textMessage);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }


    }
}
