package com.hyperionoj.judge.listener;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.judge.service.SubmitService;
import com.hyperionoj.judge.vo.RunResult;
import com.hyperionoj.judge.vo.SubmitVo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

import static com.hyperionoj.judge.constants.Constants.KAFKA_TOPIC_SUBMIT;
import static com.hyperionoj.judge.constants.Constants.KAFKA_TOPIC_SUBMIT_RESULT;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Component
public class SubmitListener {

    @Resource
    private SubmitService submitService;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = {KAFKA_TOPIC_SUBMIT}, groupId = "judgeTest")
    public void submit(ConsumerRecord<?, ?> record) throws Exception {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            RunResult runResult = submitService.submit(JSONObject.parseObject((String) kafkaMessage.get(), SubmitVo.class));
            kafkaTemplate.send(KAFKA_TOPIC_SUBMIT_RESULT, JSONObject.toJSONString(runResult));
        }
    }

}
