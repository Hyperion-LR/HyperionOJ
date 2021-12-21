package com.hyperionoj.oss.service.impl;

import com.hyperionoj.common.pojo.Mail;
import com.hyperionoj.common.service.MailService;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.oss.service.VerCodeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

import static com.hyperionoj.common.constants.Constants.SALT;
import static com.hyperionoj.common.constants.Constants.VER_CODE;

/**
 * @author Hyperion
 * @date 2021/12/2
 */
@Service
public class VerCodeServiceImpl implements VerCodeService {

    @Resource
    private MailService mailService;

    @Resource
    private RedisSever redisSever;

    /**
     * 通过邮箱获取验证码
     *
     * @param userMail 用户邮箱
     * @param subject  主题
     */
    @Override
    public void getCode(String userMail, String subject) {
        Random random = new Random();
        int code = 1000 + random.nextInt(9999);
        Mail mailBean = new Mail();
        mailBean.setSubject(subject);
        mailBean.setContent(subject + "的验证码为: " + code);
        mailBean.setRecipient(userMail);
        mailService.sendSimpleMail(mailBean);
        redisSever.setRedisKV(VER_CODE + userMail, DigestUtils.md5Hex(code + SALT));
    }

    /**
     * 检查邮箱
     *
     * @param userMail 用户邮箱
     * @param code     验证码
     * @return 是否正确
     */
    @Override
    public boolean checkCode(String userMail, String code) {
        String redisCode = redisSever.getRedisKV(VER_CODE + userMail);
        return StringUtils.compare(DigestUtils.md5Hex(code + SALT), redisCode) == 0;
    }
}
