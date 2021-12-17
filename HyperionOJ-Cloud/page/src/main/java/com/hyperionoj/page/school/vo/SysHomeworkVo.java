package com.hyperionoj.page.school.vo;

import com.hyperionoj.page.problem.vo.ProblemVo;
import com.hyperionoj.page.problem.vo.SubmitVo;
import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Data
public class SysHomeworkVo {

    private String id;

    private String classId;

    private String name;

    private String startTime;

    private String endTime;

    private String teacherId;

    private String teacherName;

    private List<ProblemVo> problems;

    private List<SubmitVo> submitVos;

}