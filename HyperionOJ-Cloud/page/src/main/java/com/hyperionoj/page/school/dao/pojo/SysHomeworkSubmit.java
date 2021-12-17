package com.hyperionoj.page.school.dao.pojo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Data
public class SysHomeworkSubmit {

    private Long id;

    private Long homeworkId;

    private Long problemId;

    private Long studentId;

    private Long submitId;

}
