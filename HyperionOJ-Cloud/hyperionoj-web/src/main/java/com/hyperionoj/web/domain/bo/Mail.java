package com.hyperionoj.web.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Data
public class Mail implements Serializable {

    /**
     * 邮件接收人
     */
    private String recipient;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

}