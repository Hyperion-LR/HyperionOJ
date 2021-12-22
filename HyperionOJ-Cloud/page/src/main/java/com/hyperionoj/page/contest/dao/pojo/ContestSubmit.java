package com.hyperionoj.page.contest.dao.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Gui
 * @date 2021/12/11
 */
@Data
public class ContestSubmit {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long contestsId;

    private Long problemId;

    private Long authorId;

    private String codeLang;

    private Long submitId;

    private Integer status;

    private Integer runTime;

    private Integer runMemory;
}
