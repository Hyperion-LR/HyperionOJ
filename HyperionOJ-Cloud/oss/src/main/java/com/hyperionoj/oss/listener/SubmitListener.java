package com.hyperionoj.oss.listener;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.vo.UpdateSubmitVo;
import com.hyperionoj.oss.dao.mapper.sys.SysUserMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Optional;

import static com.hyperionoj.common.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Component
public class SubmitListener {

    @Resource
    private SysUserMapper sysUserMapper;

    @KafkaListener(topics = KAFKA_TOPIC_SUBMIT_PAGE, groupId = "ossTest")
    public void updateSubmit(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            UpdateSubmitVo updateSubmitVo = JSONObject.parseObject((String) kafkaMessage.get(), UpdateSubmitVo.class);
            Long authorId = updateSubmitVo.getAuthorId();
            String status = updateSubmitVo.getStatus();
            if(AC.equals(status)){
                sysUserMapper.updateSubmitAc(authorId);
            }else{
                sysUserMapper.updateSubmitNoAc(authorId);
            }

        }
    }

}
