package com.hyperionoj.page.school.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Data
public class SysClass {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long teacherId;

    private String teacherName;

    private String courseName;

    private String academy;

}
