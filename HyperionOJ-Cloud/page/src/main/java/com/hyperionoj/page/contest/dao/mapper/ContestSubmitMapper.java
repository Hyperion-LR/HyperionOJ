package com.hyperionoj.page.contest.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.common.vo.page.RankVo;
import com.hyperionoj.page.contest.dao.pojo.ContestSubmit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Gui
 * @date 2021/12/11
 */
public interface ContestSubmitMapper extends BaseMapper<ContestSubmit> {
    /**
     * 排行榜
     *
     * @param contestId 比赛id
     * @return 用户id
     */
    List<RankVo> rank(@Param(("contestId")) Long contestId);
}
