package com.ksea.activemq.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by ksea on 2017/6/1.
 * 消息生产者
 */
public class JmsProducer {
    //默认连接用户名
    private static String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接用户密码
    private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接url地址
    private static String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    //设置发送的数据条数
    private static int SEND_NUM = 10;

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory;
        //连接对象
        Connection connection = null;
        //获取Session回话，用于发送或者接受信息的线程
        Session session;
        //消息目的地di
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;

        try {
            //实例化连接工厂
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            //通过连接工厂获取connection
            connection = connectionFactory.createConnection();
            connection.start();//开启连接
            //创建会话session，此处为true，表示开启事务
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建目的地 对于生产者来说目的地就是队列
            destination = session.createQueue("ksea");//指明队列名称是ksea
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //执行发送消息
            sendMessage(session, messageProducer);
            session.commit(); //提交事务
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 发送消息
     *
     * @param session         回话
     * @param messageProducer 消息生产者
     */
    private static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 1; i <= SEND_NUM; i++) {
            //创建一条文本信息
            TextMessage textMessage = session.createTextMessage("正在使用ActiveMQ发送信息:" + i);
            //发送信息
            messageProducer.send(textMessage);
        }
    }
}
