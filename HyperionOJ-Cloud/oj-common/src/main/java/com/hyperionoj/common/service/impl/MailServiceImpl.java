package com.hyperionoj.common.service.impl;

import com.hyperionoj.common.config.MailProperties;
import com.hyperionoj.common.pojo.bo.Mail;
import com.hyperionoj.common.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private MailProperties mailProperties;

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(Mail mailBean) {

        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(mailProperties.getMailSender());
        //邮件接收人
        message.setTo(mailBean.getRecipient());
        //邮件主题
        message.setSubject(mailBean.getSubject());
        //邮件内容
        message.setText(mailBean.getContent());
        try {
            javaMailSender.send(message);
            log.info("SimpleMail邮件成功发送！");
        } catch (Exception e) {
            log.error("邮件发送失败！", e.getMessage());
        }
    }

    @Override
    public void sendHtmlMail(Mail mailBean) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getMailSender());
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            StringBuilder sb = new StringBuilder();
            sb.append("<h1>SpringBoot测试邮件HTML</h1>")
                    .append("<p style='color:#01AFFD'>当您能看到这封邮件时表示SpringBoot学习项目邮件功能能够正常运转。</p>")
                    .append("<p style='text-align:right'>——冰箱的主人LR</p>");
            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMailMessage);
            log.info("HTMLMail邮件成功发送！");
        } catch (Exception e) {
            log.error("邮件发送失败!", e.getMessage());
        }
    }

    @Override
    public void sendAttachmentMail(Mail mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getMailSender());
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent());
            //文件路径
            FileSystemResource file = new FileSystemResource(ResourceUtils.getURL("classpath:").getPath() + "static" + File.separator + "res.jpeg");
            mimeMessageHelper.addAttachment("mail.jpeg", file);
            javaMailSender.send(mimeMailMessage);
            log.info("AttachmentMail邮件成功发送！");
        } catch (Exception e) {
            log.error("邮件发送失败!", e.getMessage());
        }
    }

    @Override
    public void sendInlineMail(Mail mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getMailSender());
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText("<html><body>当您能看到这封邮件时表示SpringBoot学习项目邮件功能能够正常运转。:<img src='cid:picture' /></body></html>", true);
            //文件路径
            FileSystemResource file = new FileSystemResource(ResourceUtils.getURL("classpath:").getPath() + "static" + File.separator + "res.jpeg");
            mimeMessageHelper.addInline("picture", file);

            javaMailSender.send(mimeMailMessage);
            log.info("InlineMail邮件成功发送！");
        } catch (Exception e) {
            log.error("邮件发送失败!", e.getMessage());
        }
    }
}
