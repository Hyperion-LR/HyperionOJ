package com.hyperionoj.oss.dao.pojo.contest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class Contest {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String title;

    private Long createTime;

    private Long startTime;

    private Long endTime;

    private Long runTime;

    private Integer applicationNumber;

    private Integer realNumber;

    private Integer submitNumber;

    private Integer acNumber;

    private String password;

}
