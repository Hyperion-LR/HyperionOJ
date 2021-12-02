package com.hyperionoj.oss.service;

/**
 * @author Hyperion
 * @date 2021/12/2
 */
public interface VerCodeService {

    /**
     * 通过邮箱获取验证码
     *
     * @param mail    用户邮箱
     * @param subject 验证码主题
     */
    void getCode(String mail, String subject);

    /**
     * 检查邮箱
     *
     * @param mail 用户邮箱
     * @param code 验证码
     * @return 是否正确
     */
    boolean checkCode(String mail, String code);

}
