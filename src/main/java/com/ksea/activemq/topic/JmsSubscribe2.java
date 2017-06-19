package com.ksea.activemq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by ksea on 2017/6/19.
 * 订阅者2
 */
public class JmsSubscribe2 {
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
        MessageConsumer messageConsumer;

        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            destination = session.createTopic("ksea_Topic");
            messageConsumer = session.createConsumer(destination);
            //设置消费者监听
            messageConsumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Subscribe2订阅的消息:" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
