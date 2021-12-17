package com.hyperionoj.page.school.vo;

import com.hyperionoj.common.vo.SysUserVo;
import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Data
public class SysClassVo {

    private String id;

    private String teacherId;

    private String teacherName;

    private String courseName;

    private String academy;

    private List<SysUserVo> students;

}
