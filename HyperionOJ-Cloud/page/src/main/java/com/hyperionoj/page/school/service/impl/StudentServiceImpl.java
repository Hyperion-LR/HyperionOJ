package com.hyperionoj.page.school.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.pojo.Teacher;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.SysUserVo;
import com.hyperionoj.page.problem.service.ProblemService;
import com.hyperionoj.page.problem.vo.ProblemVo;
import com.hyperionoj.page.problem.vo.RunResult;
import com.hyperionoj.page.problem.vo.SubmitVo;
import com.hyperionoj.page.school.dao.mapper.*;
import com.hyperionoj.page.school.dao.pojo.*;
import com.hyperionoj.page.school.service.StudentService;
import com.hyperionoj.page.school.vo.SysClassVo;
import com.hyperionoj.page.school.vo.SysHomeworkVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");

    @Resource
    private ProblemService problemService;

    @Resource
    private HomeworkMapper homeworkMapper;

    @Resource
    private HomeworkProblemMapper homeworkProblemMapper;

    @Resource
    private HomeworkSubmitMapper homeworkSubmitMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private ClassStudentMapper classStudentMapper;

    @Resource
    private OSSClients ossClients;

    /**
     * 获取班级列表
     *
     * @return 班级列表
     */
    @Override
    public List<SysClassVo> getClassList() {
        Teacher teacher = JSONObject.parseObject((String) ThreadLocalUtils.get(), Teacher.class);
        LambdaQueryWrapper<SysClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysClass::getTeacherId, teacher.getId());
        List<SysClass> sysClasses = classMapper.selectList(queryWrapper);
        ArrayList<SysClassVo> classList = new ArrayList<>();
        for (SysClass sysClass : sysClasses) {
            classList.add(classToVo(sysClass, false));
        }
        return classList;
    }

    private SysClassVo classToVo(SysClass sysClass, boolean isStudent) {
        SysClassVo classVo = new SysClassVo();
        classVo.setId(sysClass.getId().toString());
        classVo.setAcademy(sysClass.getAcademy());
        classVo.setTeacherId(sysClass.getTeacherId().toString());
        classVo.setTeacherName(sysClass.getTeacherName());
        classVo.setCourseName(sysClass.getCourseName());
        if (isStudent) {
            ArrayList<SysUserVo> students = new ArrayList<>();
            LambdaQueryWrapper<SysClassStudent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysClassStudent::getClassId, sysClass.getId());
            List<SysClassStudent> sysClassStudents = classStudentMapper.selectList(queryWrapper);
            for (SysClassStudent classStudent : sysClassStudents) {
                students.add(SysUserVo.userToVo(ossClients.findUserById(classStudent.getStudentNumber().toString()).getData()));
            }
            classVo.setStudents(students);
        }
        return classVo;
    }

    /**
     * 获取作业列表
     *
     * @return 作业列表
     */
    @Override
    public List<SysHomeworkVo> getHomeworkList(Long classId) {
        LambdaQueryWrapper<SysHomework> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysHomework::getClassId, classId);
        return homeworksToVo(homeworkMapper.selectList(queryWrapper));
    }

    /**
     * 获取作业详情
     *
     * @param id 作业id
     * @return 作业详情
     */
    @Override
    public SysHomeworkVo getHomework(Long id) {
        return homeworkToVo(homeworkMapper.selectById(id), true, true);
    }

    /**
     * 提交作业
     *
     * @param submitVo 代码
     * @return 运行结果
     */
    @Override
    public Object submit(SubmitVo submitVo, Long homeworkId) {
        SysUser student = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        RunResult result = (RunResult) problemService.submit(submitVo);
        SysHomeworkSubmit submit = new SysHomeworkSubmit();
        submit.setHomeworkId(homeworkId);
        submit.setStudentId(Long.valueOf(student.getStudentNumber()));
        submit.setProblemId(Long.valueOf(submitVo.getProblemId()));
        submit.setSubmitId(result.getSubmitId());
        homeworkSubmitMapper.insert(submit);
        return result;
    }

    /**
     * 加入班级
     *
     * @param id 班级id
     * @return 班级列表
     */
    @Override
    public SysClassVo joinClass(Long id) {
        SysUser student = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        SysClassStudent classStudent = new SysClassStudent();
        classStudent.setClassId(id);
        classStudent.setStudentNumber(Long.valueOf(student.getStudentNumber()));
        classStudentMapper.insert(classStudent);
        return getSysClass(id);
    }

    /**
     * 获取班级信息
     *
     * @param id 班级id
     * @return 班级信息
     */
    @Override
    public SysClassVo getSysClass(Long id) {
        SysClassVo classVo = new SysClassVo();
        SysClass sysClass = classMapper.selectById(id);
        if (sysClass == null) {
            return null;
        }
        classVo.setId(sysClass.getId().toString());
        classVo.setCourseName(sysClass.getCourseName());
        classVo.setTeacherId(sysClass.getTeacherId().toString());
        classVo.setTeacherName(sysClass.getTeacherName());
        classVo.setAcademy(sysClass.getAcademy());
        LambdaQueryWrapper<SysClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysClassStudent::getClassId, id);
        List<SysClassStudent> sysClassStudents = classStudentMapper.selectList(queryWrapper);
        ArrayList<SysUserVo> students = new ArrayList<>();
        for (SysClassStudent sysClassStudent : sysClassStudents) {
            students.add(SysUserVo.userToVo(ossClients.findUserById(sysClassStudent.getStudentNumber().toString()).getData()));
        }
        classVo.setStudents(students);
        return classVo;
    }

    @Override
    public List<SysHomeworkVo> homeworksToVo(List<SysHomework> homeworks) {
        ArrayList<SysHomeworkVo> homeworkVos = new ArrayList<>();
        for (SysHomework homework : homeworks) {
            homeworkVos.add(homeworkToVo(homework, false, false));
        }
        return homeworkVos;
    }

    @Override
    public SysHomeworkVo homeworkToVo(SysHomework homework, boolean isProblem, boolean isSubmit) {
        SysHomeworkVo sysHomeworkVo = new SysHomeworkVo();
        if (homework == null) {
            return null;
        }
        sysHomeworkVo.setId(homework.getId().toString());
        sysHomeworkVo.setClassId(homework.getClassId().toString());
        sysHomeworkVo.setName(homework.getName());
        sysHomeworkVo.setTeacherId(homework.getTeacherId().toString());
        sysHomeworkVo.setTeacherName(classMapper.selectById(homework.getTeacherId()).getTeacherName());
        sysHomeworkVo.setStartTime(dateFormat.format(homework.getStartTime()));
        sysHomeworkVo.setEndTime(dateFormat.format(homework.getEndTime()));
        if (isProblem) {
            Long homeworkId = homework.getId();
            LambdaQueryWrapper<SysHomeworkProblem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysHomeworkProblem::getHomeworkId, homeworkId);
            List<SysHomeworkProblem> sysHomeworkProblems = homeworkProblemMapper.selectList(queryWrapper);
            ArrayList<ProblemVo> problems = new ArrayList<>();
            for (SysHomeworkProblem homeworkProblem : sysHomeworkProblems) {
                problems.add(problemService.getProblemById(homeworkProblem.getProblemId()));
            }
            sysHomeworkVo.setProblems(problems);
        }
        if (isSubmit) {
            Long homeworkId = homework.getId();
            LambdaQueryWrapper<SysHomeworkSubmit> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysHomeworkSubmit::getHomeworkId, homeworkId);
            List<SysHomeworkSubmit> sysHomeworkSubmits = homeworkSubmitMapper.selectList(queryWrapper);
            ArrayList<SubmitVo> submitVos = new ArrayList<>();
            for (SysHomeworkSubmit submit : sysHomeworkSubmits) {
                submitVos.add(problemService.getSubmitById(submit.getSubmitId()));
            }
            sysHomeworkVo.setSubmitVos(submitVos);
        }
        return sysHomeworkVo;
    }
}
