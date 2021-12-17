package com.hyperionoj.page.school.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.problem.vo.SubmitVo;
import com.hyperionoj.page.school.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@RestController
@RequestMapping("/school/user")
public class UserController {

    @Resource
    private StudentService studentService;

    @GetMapping("/class/{id}")
    public Result getSysClass(@PathVariable("id") Long id) {
        return Result.success(studentService.getSysClass(id));
    }

    @PostMapping("/class/join/{id}")
    public Result joinClass(@PathVariable("id") Long id) {
        return Result.success(studentService.joinClass(id));
    }

    @GetMapping("/homeworks/{id}")
    public Result getHomeworkList(@PathVariable("id") Long classId) {
        return Result.success(studentService.getHomeworkList(classId));
    }

    @GetMapping("/homework/{id}")
    public Result getHomework(@PathVariable("id") Long id) {
        return Result.success(studentService.getHomework(id));
    }

    @PostMapping("/submit/{id}")
    public Result submitHomework(@RequestBody SubmitVo submitVo, @PathVariable("id") Long homeworkId) {
        return Result.success(studentService.submit(submitVo, homeworkId));
    }

}
