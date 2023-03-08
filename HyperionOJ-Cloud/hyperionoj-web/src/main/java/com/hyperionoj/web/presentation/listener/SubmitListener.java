package com.hyperionoj.web.presentation.listener;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.judge.dto.UpdateSubmitDO;
import com.hyperionoj.web.domain.repo.UserRepo;
import com.hyperionoj.web.infrastructure.constants.Constants;
import com.hyperionoj.web.infrastructure.mapper.UserMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Component
public class SubmitListener {

    @Resource
    private UserRepo userRepo;

    @KafkaListener(topics = Constants.KAFKA_TOPIC_SUBMIT_PAGE, groupId = "ossTest")
    public void updateSubmit(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            UpdateSubmitDO updateSubmitVo = JSONObject.parseObject((String) kafkaMessage.get(), UpdateSubmitDO.class);
            Long authorId = updateSubmitVo.getAuthorId();
            String status = updateSubmitVo.getStatus();
            if (Constants.AC.equals(status)) {
                userRepo.updateSubmitAc(authorId);
            } else {
                userRepo.updateSubmitNoAc(authorId);
            }

        }
    }

}
