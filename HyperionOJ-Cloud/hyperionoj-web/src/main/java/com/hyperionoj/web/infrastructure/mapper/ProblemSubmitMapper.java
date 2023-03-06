package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.web.infrastructure.po.ProblemSubmitPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface ProblemSubmitMapper extends BaseMapper<ProblemSubmitPO> {
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
    IPage<ProblemSubmitPO> getSubmitList(Page<ProblemSubmitPO> page,
                                       @Param("problemId") String problemId,
                                       @Param("codeLang") String codeLang,
                                       @Param("username") String username,
                                       @Param("verdict") String verdict);


}
