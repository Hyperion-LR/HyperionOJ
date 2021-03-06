package com.hyperionoj.page.school.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.page.*;
import com.hyperionoj.page.problem.service.ProblemService;
import com.hyperionoj.page.school.dao.mapper.*;
import com.hyperionoj.page.school.dao.pojo.*;
import com.hyperionoj.page.school.service.StudentService;
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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

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
        SysUser student = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        LambdaQueryWrapper<SysClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysClassStudent::getStudentNumber, student.getStudentNumber());
        List<SysClassStudent> sysClassStudents = classStudentMapper.selectList(queryWrapper);
        ArrayList<SysClassVo> classVos = new ArrayList<>();
        for (SysClassStudent classStudent : sysClassStudents) {
            SysClass sysClass = classMapper.selectById(classStudent.getClassId());
            if (sysClass != null) {
                classVos.add(classToVo(sysClass, false));
            }
        }
        return classVos;
    }

    private SysClassVo classToVo(SysClass sysClass, boolean isStudent) {
        SysClassVo classVo = new SysClassVo();
        classVo.setId(sysClass.getId().toString());
        classVo.setTeacherId(sysClass.getTeacherId().toString());
        classVo.setTeacherName(sysClass.getTeacherName());
        classVo.setAcademy(sysClass.getAcademy());
        classVo.setCourseName(sysClass.getCourseName());
        if (isStudent) {
            LambdaQueryWrapper<SysClassStudent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysClassStudent::getClassId, sysClass.getId());
            List<SysClassStudent> sysClassStudents = classStudentMapper.selectList(queryWrapper);
            ArrayList<SysUserVo> students = new ArrayList<>();
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
     * @param isProblem 是否需要加上题目
     * @param isSubmit  是否加上提交
     * @param id        作业id
     * @return 作业详情
     */
    @Override
    public SysHomeworkVo getHomework(Long id, Boolean isProblem, Boolean isSubmit) {
        return homeworkToVo(homeworkMapper.selectById(id), isProblem, isSubmit);
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
        LambdaQueryWrapper<SysClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysClassStudent::getStudentNumber, classStudent.getStudentNumber());
        queryWrapper.eq(SysClassStudent::getClassId, classStudent.getClassId());
        queryWrapper.last("limit 1");
        SysClassStudent one = classStudentMapper.selectOne(queryWrapper);
        if (one != null) {
            return null;
        }
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
            students.add(SysUserVo.userToVo(ossClients.findUserByStudentNumber(sysClassStudent.getStudentNumber().toString()).getData()));
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
        LambdaQueryWrapper<SysClass> queryWrapperTeacher = new LambdaQueryWrapper<>();
        queryWrapperTeacher.eq(SysClass::getTeacherId, homework.getTeacherId());
        queryWrapperTeacher.last("limit 1");
        sysHomeworkVo.setTeacherName(classMapper.selectOne(queryWrapperTeacher).getTeacherName());
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
