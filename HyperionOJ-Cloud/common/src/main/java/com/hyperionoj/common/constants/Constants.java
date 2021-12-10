package com.hyperionoj.common.constants;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface Constants {
    /**
     * 状态码成功
     */
    int SUCCESS_CODE = 200;

    /**
     * 用户密码加密盐
     */
    String SALT = "HyperionOJ";

    /**
     * 邮箱符号: '@'
     */
    Character AT = '@';

    /**
     * 分隔符英文句号: '.'
     */
    Character PERIOD = '.';

    /**
     * 下划线: '_'
     */
    String UNDERLINE = "_";

    /**
     * redis分隔符英文冒号: ':'
     */
    Character COLON = ':';

    /**
     * redis存token时的首部字符
     */
    String TOKEN = "TOKEN:";

    /**
     * redis中验证码首部字符
     */
    String VER_CODE = "VER_CODE:";

    /**
     * 验证码长度为6
     */
    Integer CODE_LENGTH = 6;

    /**
     * 请求头里面的字段: UNKNOWN
     */
    String UNKNOWN = "UNKNOWN";

    /**
     * 请求头里面的字段: UNDEFINED
     */
    String UNDEFINED = "UNDEFINED";

    /**
     * 表示逗号 ","
     */
    String COMMA = ",";

}
