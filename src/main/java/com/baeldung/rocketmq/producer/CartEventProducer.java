package com.baeldung.rocketmq.producer;


import com.baeldung.rocketmq.consumer.CartEventConsumer;
import com.baeldung.rocketmq.event.CartItemEvent;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class CartEventProducer {

    private Logger logger = LoggerFactory.getLogger(CartEventConsumer.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    //@Scheduled(fixedDelay = 5 * 1000)
    @GetMapping("/test")
    public void sendMsg(@RequestParam int id) {
        CartItemEvent event = new CartItemEvent(id, "bike" + id, 1);
        logger.info("[Send Msg] send msg " + event);

        rocketMQTemplate.convertAndSend("cart-item-add-topic", event);
    }
}
