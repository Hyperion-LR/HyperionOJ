package com.hyperionoj.web.domain.submit.component;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.MailService;
import com.hyperionoj.web.domain.bo.Mail;
import com.hyperionoj.web.domain.repo.JobWorkingRepo;
import com.hyperionoj.web.domain.repo.UserRepo;
import com.hyperionoj.web.infrastructure.constants.JobEventEnum;
import com.hyperionoj.web.infrastructure.po.JobBasePO;

import com.hyperionoj.web.infrastructure.po.JobWorkingPO;
import com.hyperionoj.web.infrastructure.po.UserPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;


/**
 * 用户行为动作操作推送/通知
 * @author Hyperion
 */
@Component
public class JobEventComponent {

    private static final Logger log = LoggerFactory.getLogger(JobEventComponent.class);

    @Resource
    private UserRepo userRepo;

    @Resource
    private MailService mailService;

    public void sendJobBaseEvent(JobBasePO job, JobEventEnum jobEventEnum) {
        UserPO userPO = userRepo.getById(job.getOwnerId());
        Mail mail = Mail.builder()
                .subject(job.getName() + " " + jobEventEnum.getType())
                .content(userPO.getUsername() +
                        ", 你的job: " + job.getName() +
                        "在" + new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()) + jobEventEnum.getDescription() +
                        " 详情请登录HyperionOJ查询")
                .recipient(userPO.getMail())
                .build();
        mailService.sendSimpleMail(mail);
    }
}
