package com.hyperionoj.web.presentation.controller;

import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import com.hyperionoj.web.presentation.vo.Result;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping()
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private RedisUtils redisUtils;

    @Value("${health.value}")
    private int testConfValue;

    private String kafkaResult = "no massage !";

    private static final String KAFKA_TEST_TOPIC = "kafka_test_topic";

    @GetMapping("/health")
    public Result health() {
        return Result.success("success!!! and Nacos config is " + (testConfValue == 1));
    }

    @KafkaListener(topics = {KAFKA_TEST_TOPIC}, groupId = "pageTest")
    public void kafkaListener(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        log.info("kafka listener a message: " + record);
        kafkaMessage.ifPresent(o -> kafkaResult = o.toString());
    }

    @PostMapping("/kafka/{message}")
    public Result sendKafka(@PathVariable String message){
        kafkaTemplate.send(KAFKA_TEST_TOPIC, message);
        return Result.success("ok");
    }

    @GetMapping("/kafka")
    public Result getKafka(){
        return Result.success(kafkaResult);
    }

    @PostMapping("/redis/{key}/{value}")
    public Result sendRedis(@PathVariable String key, @PathVariable String value){
        redisUtils.setRedisKV(key, value);
        return Result.success("ok");
    }

    @GetMapping("/redis/{key}")
    public Result getRedis(@PathVariable String key){
        String value = redisUtils.getRedisKV(key);
        return Result.success(value);
    }



}
