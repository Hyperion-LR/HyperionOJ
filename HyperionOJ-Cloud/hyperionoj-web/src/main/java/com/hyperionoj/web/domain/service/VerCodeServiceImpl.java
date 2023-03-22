package com.hyperionoj.web.domain.service;

import com.hyperionoj.web.application.api.MailService;
import com.hyperionoj.web.application.api.VerCodeService;
import com.hyperionoj.web.domain.bo.Mail;
import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

import static com.hyperionoj.web.infrastructure.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/2
 */
@Service
public class VerCodeServiceImpl implements VerCodeService {

    @Resource
    private MailService mailService;

    @Resource
    private RedisUtils redisSever;

    /**
     * 通过邮箱获取验证码
     *
     * @param userMail 用户邮箱
     * @param subject  主题
     */
    @Override
    public void getCode(String userMail, String subject) {
        Random random = new Random();
        int code = 1000 + random.nextInt(8999);
        Mail mailBean = new Mail();
        mailBean.setSubject(subject);
        mailBean.setContent(subject + "的验证码为: " + code);
        mailBean.setRecipient(userMail);
        mailService.sendSimpleMail(mailBean);
        redisSever.setRedisKV(getCheckCodeKKey(subject, userMail), DigestUtils.md5Hex(code + SALT));
    }

    /**
     * 检查邮箱
     *
     * @param userMail 用户邮箱
     * @param code     验证码
     * @return 是否正确
     */
    @Override
    public boolean checkCode(String subject, String userMail, String code) {
        String codeKKey = getCheckCodeKKey(subject, userMail);
        String redisCode = redisSever.getRedisKV(codeKKey);
        redisSever.delKey(codeKKey);
        return StringUtils.compare(DigestUtils.md5Hex(code + SALT), redisCode) == 0;
    }

    private String getCheckCodeKKey(String subject, String userMail){
        return VER_CODE + subject + COLON + userMail;
    }

}
