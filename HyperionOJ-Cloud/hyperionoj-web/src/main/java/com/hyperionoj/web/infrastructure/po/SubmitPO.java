package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("submit")
public class SubmitPO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private Long problemId;

    private Long authorId;

    private String codeLang;

    private String codeBody;

    private String status;

    private Integer runTime;

    private Integer runMemory;

    private Long createTime;

}