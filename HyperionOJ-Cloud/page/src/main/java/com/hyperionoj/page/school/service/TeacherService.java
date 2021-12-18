package com.hyperionoj.page.school.service;

import com.hyperionoj.page.problem.vo.SubmitVo;
import com.hyperionoj.page.school.vo.SysClassVo;
import com.hyperionoj.page.school.vo.SysHomeworkVo;
import com.hyperionoj.page.school.vo.params.SysClassParam;
import com.hyperionoj.page.school.vo.params.SysHomeworkParam;

import java.text.ParseException;
import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
public interface TeacherService {

    /**
     * 获取班级列表
     *
     * @return 班级列表
     */
    List<SysClassVo> getClassList();

    /**
     * 创建班级
     *
     * @param classVo 班级对象
     * @return 结果
     */
    SysClassVo create(SysClassParam classVo);

    /**
     * 添加学生
     *
     * @param studentId 学生学号
     * @param classId   班级id
     */
    void addStudent(String studentId, Long classId);

    /**
     * 移除学生
     *
     * @param studentId 学生学号
     * @param classId   班级id
     */
    void removeStudent(String studentId, Long classId);

    /**
     * 发布作业
     *
     * @param homeworkParam 作业参数
     * @return 作业情况
     * @throws ParseException 格式错误
     */
    SysHomeworkVo pushHomework(SysHomeworkParam homeworkParam) throws ParseException;

    /**
     * 获取作业列表
     *
     * @return 该老师发布的作业列表
     */
    List<SysHomeworkVo> getHomeworks();

    /**
     * 通过id获取作业
     *
     * @param id 作业id
     * @return 作业情况
     */
    SysHomeworkVo getHomeworkById(Long id);

    /**
     * 更新作业
     * @exception ParseException 格式错误
     * @param homeworkParam 作业参数
     * @return 作业情况
     */
    SysHomeworkVo updateHomework(SysHomeworkParam homeworkParam) throws ParseException;

    /**
     * 删除作业
     *
     * @param homeworkId 要删除的作业id
     * @return 目前的作业列表
     */
    List<SysHomeworkVo> deleteHomework(String homeworkId);

    /**
     * 获取提交列表
     *
     * @param homeworkId 作业id
     * @return 改作业学生的提交情况
     */
    List<SubmitVo> getSubmits(Long homeworkId);
}
