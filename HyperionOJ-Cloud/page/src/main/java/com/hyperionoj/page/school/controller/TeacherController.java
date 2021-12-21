package com.hyperionoj.page.school.controller;

import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.params.SysClassParam;
import com.hyperionoj.common.vo.params.SysHomeworkParam;
import com.hyperionoj.page.school.service.StudentService;
import com.hyperionoj.page.school.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@RestController
@RequestMapping("/school/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    @GetMapping("/class")
    public Result getClassList() {
        return Result.success(teacherService.getClassList());
    }

    @GetMapping("/class/{id}")
    public Result getSysClass(@PathVariable("id") Long id) {
        return Result.success(teacherService.getSysClass(id));
    }

    @PostMapping("/create")
    public Result createClass(@RequestBody SysClassParam classParam) {
        return Result.success(teacherService.create(classParam));
    }

    @PostMapping("/add/{id}")
    public Result addStudent(@RequestBody SysUser student, @PathVariable("id") Long classId) {
        teacherService.addStudent(student.getStudentNumber(), classId);
        return Result.success(this.getSysClass(classId));
    }

    @PostMapping("/remove/{id}")
    public Result removeStudent(@RequestBody SysUser student, @PathVariable("id") Long classId) {
        teacherService.removeStudent(student.getStudentNumber(), classId);
        return Result.success(this.getSysClass(classId));
    }

    @PostMapping("/push")
    public Result pushHomework(@RequestBody SysHomeworkParam homeworkParam) throws ParseException {
        return Result.success(teacherService.pushHomework(homeworkParam));
    }

    @GetMapping("/homeworks/{id}")
    public Result getHomeworkList(@PathVariable("id") Long classId) {
        return Result.success(teacherService.getHomeworks(classId));
    }

    @GetMapping("/homework/{id}")
    public Result getHomeworkId(@PathVariable("id") Long id) {
        return Result.success(teacherService.getHomeworkById(id));
    }

    @PostMapping("update/homework")
    public Result updateHomework(@RequestBody SysHomeworkParam homeworkParam) throws ParseException {
        return Result.success(teacherService.updateHomework(homeworkParam));
    }

    @DeleteMapping("delete/homework")
    private Result deleteHomework(@RequestBody SysHomeworkParam homeworkParam) {
        return Result.success(teacherService.deleteHomework(homeworkParam.getId().toString()));
    }

    @GetMapping("/submits/{id}")
    public Result submitList(@PathVariable("id") Long homeworkId) {
        return Result.success(teacherService.getSubmits(homeworkId));
    }

}
