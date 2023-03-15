package com.hyperionoj.web.domain.submit.component;


import com.hyperionoj.web.infrastructure.constants.JobEventEnum;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;


/**
 * 用户行为动作操作推送/通知
 * @author Hyperion
 */
@Component
@Slf4j
public class JobEventComponent {


    public void sendJobBaseEvent(JobBasePO job, JobEventEnum jobEventEnum) {

    }
}
