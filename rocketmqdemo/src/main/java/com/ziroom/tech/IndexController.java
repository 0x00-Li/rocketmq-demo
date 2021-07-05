package com.ziroom.tech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lidm
 * @Date 2020/3/31
 */
@RestController
public class IndexController {
    @Autowired
    RocketProducer rocketProducer;
    @Autowired
    RocketConsumer rocketConsumer;
    @GetMapping("producer")
    public ResponseEntity producer(){
        rocketProducer.clientMQProducer();
        return ResponseEntity.ok("success");
    }
    @GetMapping("tpl/producer")
    public ResponseEntity tplproducer(){
        rocketProducer.tplProduer();
        return ResponseEntity.ok("success");
    }
    @GetMapping("consumer")
    public ResponseEntity consumer(){
        rocketConsumer.clientMQPushConsumer();
        return ResponseEntity.ok("success");
    }
}
