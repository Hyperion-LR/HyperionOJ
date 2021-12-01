package com.hyperionoj.test;

import com.hyperionoj.common.pojo.bo.Mail;
import com.hyperionoj.common.service.MailService;
import com.hyperionoj.common.service.RedisSever;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@RequestMapping("/test")
@RestController
public class TestController {
    @Resource
    private RedisSever redisSever;

    @Resource
    private MailService mailService;

    @RequestMapping("/redis")
    private String testRedis() {
        redisSever.setRedisKV("test", "test");
        return redisSever.getRedisKV("test");
    }

    @RequestMapping("/mail/{mail}")
    private void testMail(@PathVariable("mail") String Email) {
        Mail mail = new Mail();
        mail.setContent("123");
        mail.setRecipient(Email);
        mail.setSubject("test");
        mailService.sendHtmlMail(mail);
    }

}
