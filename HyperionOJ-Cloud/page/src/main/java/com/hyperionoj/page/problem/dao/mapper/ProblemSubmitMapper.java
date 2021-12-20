package com.hyperionoj.page.problem.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.page.problem.dao.dos.ProblemArchives;
import com.hyperionoj.page.problem.dao.pojo.ProblemSubmit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface ProblemSubmitMapper extends BaseMapper<ProblemSubmit> {
    /**
     * 获取提交列表
     *
     * @param page      分页查询参数
     * @param problemId 题目id
     * @param codeLang  代码语言
     * @param username  用户名
     * @param verdict   提交结果
     * @return 根据分页参数返回简要提交信息
     */
    IPage<ProblemSubmit> getSubmitList(Page<ProblemSubmit> page,
                                       @Param("problemId") String problemId,
                                       @Param("codeLang") String codeLang,
                                       @Param("username") String username,
                                       @Param("verdict") String verdict);

    /**
     * 获取每日题量
     *
     * @param id 用户id
     * @return 题量列表
     */
    List<ProblemArchives> getEveryday(@Param("id") Long id);

}
