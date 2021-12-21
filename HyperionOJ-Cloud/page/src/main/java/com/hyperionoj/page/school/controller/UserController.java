package com.hyperionoj.page.school.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.problem.vo.SubmitVo;
import com.hyperionoj.page.school.service.StudentService;
import com.hyperionoj.page.school.vo.SysClassVo;
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

    @GetMapping("/class")
    public Result getClassList() {
        return Result.success(studentService.getClassList());
    }

    @GetMapping("/class/{id}")
    public Result getSysClass(@PathVariable("id") Long id) {
        return Result.success(studentService.getSysClass(id));
    }

    @PostMapping("/class/join/{id}")
    public Result joinClass(@PathVariable("id") Long id) {
        SysClassVo classVo = studentService.joinClass(id);
        if (classVo != null) {
            return Result.success(classVo);
        } else {
            return Result.success("已经在班级里了!");
        }
    }

    @GetMapping("/homeworks/{id}")
    public Result getHomeworkList(@PathVariable("id") Long classId) {
        return Result.success(studentService.getHomeworkList(classId));
    }

    @GetMapping("/homework/{id}")
    public Result getHomework(@PathVariable("id") Long id) {
        return Result.success(studentService.getHomework(id, true, true));
    }

    @PostMapping("/submit/{id}")
    public Result submitHomework(@PathVariable("id") Long homeworkId, @RequestBody String submitVo) {
        return Result.success(studentService.submit(JSONObject.parseObject(submitVo, SubmitVo.class), homeworkId));
    }

}
