package com.hyperionoj.oss.dao.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class SysClass {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long teacherId;

    private String courseName;

    private String academy;

}
