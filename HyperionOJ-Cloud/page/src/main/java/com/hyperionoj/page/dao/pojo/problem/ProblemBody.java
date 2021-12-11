package com.hyperionoj.page.dao.pojo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class ProblemBody {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String problemBody;

    private String problemBodyHtml;

}
