package com.ziroom.tech;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @Description
 * @Author lidm
 * @Date 2020/3/31
 */
@Service
public class RocketProducerMallTest {
    @Autowired
    TaskExecutor taskExecutor;
    @Autowired
    RocketConfig rocketConfig;
    // @PostConstruct //@PostContruct是spring框架的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
    public void clientMQProducer() {
        //生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer(rocketConfig.getProducerGroup());

        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(rocketConfig.getNsAddr());

        try {
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();

            //创建一个消息实例，包含 topic、tag 和 消息体
            //如下：topic 为 "TopicTest"，tag 为 "push"
            Message message = new Message(rocketConfig.getTopic(), "push", "发送消息----zhisheng-----".getBytes());

            StopWatch stop = new StopWatch();
            stop.start();

            for (int i = 0; i < 1000; i++) {
                SendResult result = producer.send(message, new MessageQueueSelector() {

                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                }, 1);
                System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }
            stop.stop();
            System.out.println("----------------发送十条消息耗时：" + stop.getTotalTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }


    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public void tplProduer(){

        for (int i=0;i<1000;i++){
//            taskExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    rocketMQTemplate.convertAndSend(rocketConfig.getTopic(),"this is message");
//                    System.out.println("发送成功："+Thread.currentThread().getId());
//                }
//            });
            rocketMQTemplate.convertAndSend(rocketConfig.getTopic(),"this is message");
//            rocketMQTemplate.asyncSend(rocketConfig.getTopic(), "this is message", new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println(sendResult.getMsgId());
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    System.out.println(throwable.getMessage());
//                }
//            });

            System.out.println("发送成功："+Thread.currentThread().getId());

        }

    }
}
