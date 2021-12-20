package com.hyperionoj.page.contest.dao.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Gui
 * @date 2021/12/11
 */
@Data
public class ContestUser {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long contestsId;

    private Long userId;
}
