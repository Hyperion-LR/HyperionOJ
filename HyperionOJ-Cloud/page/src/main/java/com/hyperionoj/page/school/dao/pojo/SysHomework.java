package com.hyperionoj.page.school.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Data
public class SysHomework {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long classId;

    private String name;

    private Long startTime;

    private Long endTime;

    private Long teacherId;

}
