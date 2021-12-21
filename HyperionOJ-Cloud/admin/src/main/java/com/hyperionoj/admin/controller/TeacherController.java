package com.hyperionoj.admin.controller;

import com.hyperionoj.admin.aop.PermissionAnnotation;
import com.hyperionoj.common.feign.AdminPageClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.params.SysClassParam;
import com.hyperionoj.common.vo.params.SysHomeworkParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/19
 */
@RequestMapping("/teacher")
@RestController
public class TeacherController {

    @Resource
    private AdminPageClients adminPageClients;

    /**
     * 获取课程列表
     *
     * @return 课程列表
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/class", method = RequestMethod.GET)
    Result getClassList() {
        return adminPageClients.getClassList();
    }

    /**
     * 获取课程详情
     *
     * @param id 课程id
     * @return 课程详情
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/class/{id}", method = RequestMethod.GET)
    Result getSysClass(@PathVariable("id") Long id) {
        return adminPageClients.getSysClass(id);
    }

    /**
     * 创建课程
     *
     * @param classParam 班级参数
     * @return 返回班级
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    Result createClass(@RequestBody SysClassParam classParam) {
        return adminPageClients.createClass(classParam);
    }

    /**
     * 往课程添加学生
     *
     * @param student 学生参数
     * @param classId 课程id
     * @return 是否加入成功
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    Result addStudent(@RequestBody SysUser student, @PathVariable("id") Long classId) {
        return adminPageClients.addStudent(student, classId);
    }

    /**
     * 移除学生
     *
     * @param student 学生参数
     * @param classId 课程id
     * @return 一处结果
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    Result removeStudent(@RequestBody SysUser student, @PathVariable("id") Long classId) {
        return adminPageClients.removeStudent(student, classId);
    }

    /**
     * 发布作业
     *
     * @param homeworkParam 作业参数
     * @return 作业结果
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    Result pushHomework(@RequestBody SysHomeworkParam homeworkParam) {
        return adminPageClients.pushHomework(homeworkParam);
    }

    /**
     * 查看作业列表
     *
     * @param classId 课程id
     * @return 作业列表
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/homeworks/{id}", method = RequestMethod.GET)
    Result getHomeworkList(@PathVariable("id") Long classId) {
        return adminPageClients.getHomeworkList(classId);
    }

    /**
     * 查看作业详情
     *
     * @param id 作业id
     * @return 作业详情
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/homework/{id}", method = RequestMethod.GET)
    Result getHomeworkId(@PathVariable("id") Long id) {
        return adminPageClients.getHomeworkId(id);
    }

    /**
     * 更新作业
     *
     * @param homeworkParam 作业参数
     * @return 作业详情
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/update/homework", method = RequestMethod.POST)
    Result updateHomework(@RequestBody SysHomeworkParam homeworkParam) {
        return adminPageClients.updateHomework(homeworkParam);
    }

    /**
     * 删除作业
     *
     * @param homeworkParam 作业参数
     * @return 删除结果
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/delete/homework", method = RequestMethod.DELETE)
    Result deleteHomework(@RequestBody SysHomeworkParam homeworkParam) {
        return adminPageClients.deleteHomework(homeworkParam);
    }

    /**
     * 查看作业提交情况
     *
     * @param homeworkId 作业id
     * @return 提交列表
     */
    @PermissionAnnotation(level = 3)
    @RequestMapping(value = "/submits/{id}", method = RequestMethod.GET)
    Result submitList(@PathVariable("id") Long homeworkId) {
        return adminPageClients.submitList(homeworkId);
    }

}
