package com.hyperionoj.page.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.page.dao.mapper.problem.*;
import com.hyperionoj.page.dao.pojo.problem.Problem;
import com.hyperionoj.page.service.ProblemService;
import com.hyperionoj.page.vo.PageParams;
import com.hyperionoj.page.vo.ProblemVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@Service
public class ProblemServiceImpl implements ProblemService {

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private ProblemBodyMapper problemBodyMapper;

    @Resource
    private ProblemCategoryMapper problemCategoryMapper;

    @Resource
    private ProblemCommentMapper problemCommentMapper;

    @Resource
    private ProblemTagMapper problemTagMapper;

    @Resource
    private ProblemSubmitMapper problemSubmitMapper;

    /**
     * 返回题目列表
     *
     * @param pageParams 分页参数
     * @return 题目
     */
    @Override
    public List<ProblemVo> getProblemList(PageParams pageParams) {
        Page<Problem> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Problem> problemPage = problemMapper.listArticle(page, pageParams.getLevel(), pageParams.getCategoryId());
        return copyProblemList(problemPage.getRecords());
    }

    /**
     * 通过题目id获取题目
     *
     * @param id 题目id
     * @return 题目
     */
    @Override
    public ProblemVo getProblemById(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return null;
        }
        return problemToVo(problemMapper.selectById(id));
    }

    private List<ProblemVo> copyProblemList(List<Problem> problemPage) {
        List<ProblemVo> problemVoList = new ArrayList<>();
        for (Problem problem : problemPage) {
            problemVoList.add(problemToVo(problem));
        }
        return problemVoList;
    }

    private ProblemVo problemToVo(Problem problem) {
        ProblemVo problemVo = new ProblemVo();
        problemVo.setId(problem.getId().toString());
        problemVo.setTitle(problem.getTitle());
        problemVo.setBodyId(problem.getBodyId().toString());
        problemVo.setProblemLevel(problem.getProblemLevel());
        problemVo.setCategoryId(problem.getCategoryId().toString());
        problemVo.setAcNumber(problem.getAcNumber());
        problemVo.setSubmitNumber(problem.getSubmitNumber());
        problemVo.setSolutionNumber(problem.getSolutionNumber());
        problemVo.setCommentNumber(problem.getCommentNumber());
        return problemVo;
    }
}
