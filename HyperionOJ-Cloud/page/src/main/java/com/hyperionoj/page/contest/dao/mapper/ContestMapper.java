package com.hyperionoj.page.contest.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.page.contest.dao.pojo.Contest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface ContestMapper extends BaseMapper<Contest> {

    /**
     * 返回比赛归档
     *
     * @param page      分页
     * @param startTime 比赛开始时间
     * @param endTime   比赛结束时间
     * @return 比赛归档分页
     */
    IPage<Contest> contestList(Page<Contest> page, @Param("startTime") Long startTime, @Param("endTime") Long endTime);

}
