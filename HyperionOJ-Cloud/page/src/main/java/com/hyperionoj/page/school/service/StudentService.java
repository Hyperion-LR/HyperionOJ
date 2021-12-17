package com.hyperionoj.page.school.service;

import com.hyperionoj.page.problem.vo.SubmitVo;
import com.hyperionoj.page.school.dao.pojo.SysHomework;
import com.hyperionoj.page.school.vo.SysClassVo;
import com.hyperionoj.page.school.vo.SysHomeworkVo;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
public interface StudentService {

    /**
     * 获取班级列表
     *
     * @return 班级列表
     */
    List<SysClassVo> getClassList();

    /**
     * 获取作业列表
     *
     * @param classId 班级id
     * @return 作业列表
     */
    List<SysHomeworkVo> getHomeworkList(Long classId);

    /**
     * 获取作业详情
     *
     * @param id 作业id
     * @return 作业详情
     */
    SysHomeworkVo getHomework(Long id);

    /**
     * 提交作业
     *
     * @param submitVo   代码
     * @param homeworkId 作业id
     * @return 运行结果
     */
    Object submit(SubmitVo submitVo, Long homeworkId);

    /**
     * 加入班级
     *
     * @param id 班级id
     * @return 班级列表
     */
    SysClassVo joinClass(Long id);

    /**
     * 获取班级信息
     *
     * @param id 班级id
     * @return 班级信息
     */
    Object getSysClass(Long id);

    /**
     * 作业格式转换
     *
     * @param homeworks 作业对象
     * @return vo对象
     */
    List<SysHomeworkVo> homeworksToVo(List<SysHomework> homeworks);

    /**
     * 作业格式转换
     *
     * @param homework  作业对象
     * @param isProblem 是否需要题目列表
     * @param isSubmit  是否需要提交列表
     * @return vo对象
     */
    SysHomeworkVo homeworkToVo(SysHomework homework, boolean isProblem, boolean isSubmit);

}
