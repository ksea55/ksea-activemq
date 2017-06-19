package com.ksea.activemq.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by ksea on 2017/6/19.
 * 基于监听者得消费者模式，这种方式比receive就很好
 * 监听者在消息队列中如有消息将会主动监听该事件 然后执行
 */
public class JmsConsumerListener {
    //连接ActiveMQ的用户名
    private static String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //连接ActiveMQ的密码
    private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //连接A窗体veMQ的URL地址
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
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("ksea");
            messageConsumer = session.createConsumer(destination);
            //设置监听模式,这里需要一个MessageListener的实现,当然这里也可以用匿名内部类
            messageConsumer.setMessageListener(new MyListener());

        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
}

//消息监听
class MyListener implements MessageListener {

    public void onMessage(Message message) {

        try {
            TextMessage msg = (TextMessage) message;
            String msgText = msg.getText();
            System.out.println(msgText);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}