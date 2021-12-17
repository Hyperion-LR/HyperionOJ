package com.hyperionoj.page.school.vo.params;

import com.hyperionoj.page.problem.vo.ProblemVo;
import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Data
public class SysHomeworkParam {

    private Long id;

    private Long classId;

    private String name;

    private String startDate;

    private String endDate;

    private String teacherId;

    private List<ProblemVo> problemVos;

}
