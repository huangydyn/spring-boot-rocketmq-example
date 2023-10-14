package com.demo.rocketmq.consumer;

import com.demo.rocketmq.event.CartItemEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CartEventConsumer {

    private Logger logger = LoggerFactory.getLogger(CartEventConsumer.class);

    @Service
    @RocketMQMessageListener(topic = "cart-item-add-topic",
            consumerGroup = "cart-consumer_cart-item-add-topic", maxReconsumeTimes = 2)
    public class CardItemAddConsumer implements RocketMQListener<MessageExt> {

        public void onMessage(MessageExt messageExt) {
            CartItemEvent addItemEvent = new CartItemEvent();
            try {
                addItemEvent = new ObjectMapper().readValue(new String(messageExt.getBody()),
                        CartItemEvent.class);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            logger.info("[Receive Msg] item offset {} ，body {}", messageExt.getCommitLogOffset(),
                    addItemEvent);
            if (addItemEvent.getId() % 2 == 0) {
                throw new RuntimeException("[Receive Msg] consume met exception");
            }
        }
    }

    @Service
    @RocketMQMessageListener(topic = "%DLQ%cart-consumer_cart-item-add-topic",
            consumerGroup = "dlq_cart-consumer_cart-item-add-topic")
    public class CardItemAddRetryConsumer implements RocketMQListener<MessageExt> {

        public void onMessage(MessageExt messageExt) {
            CartItemEvent addItemEvent = new CartItemEvent();
            try {
                addItemEvent = new ObjectMapper().readValue(new String(messageExt.getBody()),
                        CartItemEvent.class);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            logger.info("[Receive Msg] [Retry] item offset {} ，body {}",
                    messageExt.getCommitLogOffset(), addItemEvent);
        }
    }
}
