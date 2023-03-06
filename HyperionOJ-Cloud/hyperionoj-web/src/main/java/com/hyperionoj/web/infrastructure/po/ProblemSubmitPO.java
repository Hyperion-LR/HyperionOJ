package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 题目提交PO类
 *
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class ProblemSubmitPO {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 提交者ID
     */
    private Long authorId;

    /**
     * 提交人名字（省的查）
     */
    private String username;

    /**
     * 代码语言
     */
    private String codeLang;

    /**
     * 代码
     */
    private String codeBody;

    /**
     * 运行结果:AC,WA,TLE,RE
     */
    private String status;

    /**
     * 运行时间
     */
    private Integer runTime;

    /**
     * 运行内存
     */
    private Integer runMemory;

    /**
     * 提交时间
     */
    private Long createTime;

}
