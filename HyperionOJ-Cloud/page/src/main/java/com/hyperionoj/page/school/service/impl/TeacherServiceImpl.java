package com.hyperionoj.page.school.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.Teacher;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.page.*;
import com.hyperionoj.common.vo.params.SysClassParam;
import com.hyperionoj.common.vo.params.SysHomeworkParam;
import com.hyperionoj.page.problem.service.ProblemService;
import com.hyperionoj.page.school.dao.mapper.*;
import com.hyperionoj.page.school.dao.pojo.*;
import com.hyperionoj.page.school.service.StudentService;
import com.hyperionoj.page.school.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    @Resource
    private StudentService studentService;

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

    /**
     * 获取班级详情
     *
     * @param id 班级id
     * @return 班级详情
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

    /**
     * 创建班级
     *
     * @param classParam 班级对象
     * @return 结果
     */
    @Override
    public SysClassVo create(SysClassParam classParam) {
        SysClass sysClass = new SysClass();
        sysClass.setTeacherId(Long.valueOf(classParam.getTeacherId()));
        sysClass.setTeacherName(classParam.getTeacherName());
        sysClass.setCourseName(classParam.getCourseName());
        sysClass.setAcademy(classParam.getAcademy());
        classMapper.insert(sysClass);
        SysClassVo classVo = new SysClassVo();
        classVo.setId(sysClass.getId().toString());
        classVo.setTeacherId(sysClass.getTeacherId().toString());
        classVo.setTeacherName(sysClass.getTeacherName());
        classVo.setCourseName(sysClass.getCourseName());
        classVo.setAcademy(sysClass.getAcademy());
        return classVo;
    }

    /**
     * 添加学生
     *
     * @param studentId 学生学号
     * @param classId   班级id
     */
    @Override
    public void addStudent(String studentId, Long classId) {
        SysClassStudent sysClassStudent = new SysClassStudent();
        sysClassStudent.setStudentNumber(Long.valueOf(studentId));
        sysClassStudent.setClassId(classId);
        classStudentMapper.insert(sysClassStudent);
    }

    /**
     * 移除学生
     *
     * @param studentId 学生学号
     */
    @Override
    public void removeStudent(String studentId, Long classId) {
        LambdaQueryWrapper<SysClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysClassStudent::getClassId, classId);
        queryWrapper.eq(SysClassStudent::getStudentNumber, studentId);
        classStudentMapper.delete(queryWrapper);
    }

    /**
     * 发布作业
     *
     * @param homeworkParam 作业参数
     * @return 作业情况
     */
    @Override
    public SysHomeworkVo pushHomework(SysHomeworkParam homeworkParam) throws ParseException {
        SysHomework homework = new SysHomework();
        homework.setTeacherId(Long.valueOf(homeworkParam.getTeacherId()));
        homework.setClassId(homeworkParam.getClassId());
        homework.setStartTime(dateFormat.parse(homeworkParam.getStartDate()).getTime());
        homework.setEndTime(dateFormat.parse(homeworkParam.getEndDate()).getTime());
        homework.setName(homeworkParam.getName());
        homeworkMapper.insert(homework);
        SysHomeworkProblem homeworkProblem = new SysHomeworkProblem();
        homeworkProblem.setHomeworkId(homework.getId());
        for (ProblemVo problemVo : homeworkParam.getProblemVos()) {
            homeworkProblem.setProblemId(Long.valueOf(problemVo.getId()));
            homeworkProblemMapper.insert(homeworkProblem);
            homeworkProblem.setId(null);
        }
        return getHomeworkById(homework.getId());
    }

    /**
     * 获取作业列表
     *
     * @return 该老师发布的作业列表
     */
    @Override
    public List<SysHomeworkVo> getHomeworks(Long classId) {
        Teacher teacher = JSONObject.parseObject((String) ThreadLocalUtils.get(), Teacher.class);
        LambdaQueryWrapper<SysHomework> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysHomework::getTeacherId, teacher.getId());
        queryWrapper.eq(SysHomework::getClassId, classId);
        return studentService.homeworksToVo(homeworkMapper.selectList(queryWrapper));
    }

    /**
     * 通过id获取作业
     *
     * @param id 作业id
     * @return 作业情况
     */
    @Override
    public SysHomeworkVo getHomeworkById(Long id) {
        return studentService.getHomework(id, true, false);
    }

    /**
     * 更新作业
     *
     * @param homeworkParam 作业参数
     * @return 作业情况
     */
    @Override
    public SysHomeworkVo updateHomework(SysHomeworkParam homeworkParam) throws ParseException {
        SysHomework homework = new SysHomework();
        homework.setId(homeworkParam.getId());
        homework.setClassId(homeworkParam.getClassId());
        homework.setTeacherId(Long.valueOf(homeworkParam.getTeacherId()));
        homework.setStartTime(dateFormat.parse(homeworkParam.getStartDate()).getTime());
        homework.setEndTime(dateFormat.parse(homeworkParam.getEndDate()).getTime());
        homework.setName(homeworkParam.getName());
        homeworkMapper.updateById(homework);
        SysHomeworkProblem homeworkProblem = new SysHomeworkProblem();
        homeworkProblem.setHomeworkId(homework.getId());
        LambdaQueryWrapper<SysHomeworkProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysHomeworkProblem::getHomeworkId, homeworkParam.getId());
        homeworkProblemMapper.delete(queryWrapper);
        for (ProblemVo problemVo : homeworkParam.getProblemVos()) {
            homeworkProblem.setProblemId(Long.valueOf(problemVo.getId()));
            homeworkProblemMapper.insert(homeworkProblem);
            homeworkProblem.setId(null);
        }
        return getHomeworkById(homework.getId());
    }

    /**
     * 删除作业
     *
     * @param homeworkId 要删除的作业id
     * @return 目前的作业列表
     */
    @Override
    public List<SysHomeworkVo> deleteHomework(String homeworkId) {
        homeworkMapper.deleteById(homeworkId);
        LambdaQueryWrapper<SysHomeworkProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysHomeworkProblem::getHomeworkId, homeworkId);
        homeworkProblemMapper.delete(queryWrapper);
        Teacher teacher = JSONObject.parseObject((String) ThreadLocalUtils.get(), Teacher.class);
        LambdaQueryWrapper<SysHomework> queryWrapperNew = new LambdaQueryWrapper<>();
        queryWrapperNew.eq(SysHomework::getTeacherId, teacher.getId());
        return studentService.homeworksToVo(homeworkMapper.selectList(queryWrapperNew));
    }

    /**
     * 获取提交列表
     *
     * @param homeworkId 作业id
     * @return 改作业学生的提交情况
     */
    @Override
    public List<SubmitVo> getSubmits(Long homeworkId) {
        LambdaQueryWrapper<SysHomeworkSubmit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysHomeworkSubmit::getHomeworkId, homeworkId);
        List<SysHomeworkSubmit> sysHomeworkSubmits = homeworkSubmitMapper.selectList(queryWrapper);
        List<SubmitVo> submitVoList = new ArrayList<>();
        for (SysHomeworkSubmit homeworkSubmit : sysHomeworkSubmits) {
            SubmitVo submit = problemService.getSubmitById(homeworkSubmit.getSubmitId());
            submitVoList.add(submit);
        }
        return submitVoList;
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

}
