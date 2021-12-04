package com.hyperionoj.common.service;

import com.hyperionoj.common.pojo.bo.Mail;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface MailService {
    /**
     * 发发送一个简单格式的邮件
     *
     * @param mailBean 要发送的邮件
     */
    void sendSimpleMail(Mail mailBean);

    /**
     * 发送一个HTML格式的邮件
     *
     * @param mailBean
     */
    void sendHtmlMail(Mail mailBean);

    /**
     * 发送带附件格式的邮件
     *
     * @param mailBean
     */
    void sendAttachmentMail(Mail mailBean);

    /**
     * 发送带静态资源的邮件
     *
     * @param mailBean
     */
    void sendInlineMail(Mail mailBean);
}
