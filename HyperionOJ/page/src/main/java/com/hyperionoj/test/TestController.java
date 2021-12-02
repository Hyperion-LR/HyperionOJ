package com.hyperionoj.test;

import com.hyperionoj.common.pojo.bo.Mail;
import com.hyperionoj.common.pojo.vo.ErrorCode;
import com.hyperionoj.common.pojo.vo.Result;
import com.hyperionoj.common.service.MailService;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

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

    @Resource
    private QiniuUtils qiniuUtils;

    @RequestMapping("/redis")
    private Result testRedis() {
        redisSever.setRedisKV("test", "test");
        return Result.success(redisSever.getRedisKV("test"));
    }

    @RequestMapping("/mail/{mail}")
    private void testMail(@PathVariable("mail") String Email) {
        Mail mail = new Mail();
        mail.setContent("123");
        mail.setRecipient(Email);
        mail.setSubject("test");
        mailService.sendHtmlMail(mail);
    }

    @PostMapping("/upload")
    public Result uploadImg(@RequestParam("image") MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(multipartFile, fileName);
        if (upload) {
            return Result.success(qiniuUtils.getUrl() + '/' + fileName);
        }
        return Result.fail(ErrorCode.SYSTEM_ERROR);
    }

}
