package com.hyperionoj.page.problem.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/18
 */
@Data
public class ProblemProblemTag {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long problemId;

    private Long tagId;

}
