package com.hyperionoj.web.presentation.controller.admin;

import com.hyperionoj.web.application.api.ProblemService;
import com.hyperionoj.web.presentation.dto.CategoryDTO;
import com.hyperionoj.web.presentation.dto.CommentDTO;
import com.hyperionoj.web.presentation.dto.ProblemDTO;
import com.hyperionoj.web.presentation.dto.TagDTO;
import com.hyperionoj.web.presentation.vo.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/13
 */
@RequestMapping("/admin/problem")
@RestController
public class AdminProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 添加题目分类
     *
     * @param categoryDTO 分类信息
     * @return 分类情况
     */
    @PostMapping("/category/add")
    public Result addProblemCategory(@RequestBody CategoryDTO categoryDTO) {
        return Result.success(problemService.addCategory(categoryDTO));
    }

    /**
     * 删除题目分类
     *
     * @param categoryDTO 分类参数
     */
    @DeleteMapping("/category/delete")
    public Result deleteProblemCategory(@RequestBody CategoryDTO categoryDTO) {
        problemService.deleteCategory(categoryDTO);
        return Result.success(null);
    }

    /**
     * 添加题目标签
     *
     * @param tagDTO 标签信息
     * @return 分类情况
     */
    @PostMapping(value = "/tag/add")
    public Result addProblemTag(@RequestBody TagDTO tagDTO) {
        return Result.success(problemService.addTag(tagDTO));
    }

    /**
     * 删除题目标签
     *
     * @param tagDTO 标签参数
     */
    @DeleteMapping(value = "/tag/delete")
    public Result deleteProblemTag(@RequestBody TagDTO tagDTO) {
        return Result.success(problemService.deleteTag(tagDTO));
    }

    /**
     * 添加题目
     *
     * @param problemDTO 题目对象
     * @return 新加的题目
     */
    @PostMapping(value = "/problem/add")
    public Result addProblem(@RequestBody ProblemDTO problemDTO) {
        return Result.success(problemService.addProblem(problemDTO));
    }

    /**
     * 修改题目
     *
     * @param problemDTO 题目信息
     */
    @PostMapping(value = "/problem/update")
    public Result updateProblem(@RequestBody ProblemDTO problemDTO) {
        return Result.success(problemService.updateProblem(problemDTO));
    }

    /**
     * 删除题目
     *
     * @param problemDTO 题目信息
     */
    @DeleteMapping(value = "/problem/delete")
    public Result deleteProblem(@RequestBody ProblemDTO problemDTO) {
        return Result.success(problemService.deleteProblem(problemDTO.getId()));
    }

    /**
     * 获取题目测试点
     *
     * @param problemId 题目ID
     */
    @GetMapping(value = "/case/{problemId}")
    public Result getProblemCase(Long problemId){
        problemService.getProblemCase(problemId);
        return Result.success(null);
    }

    /**
     * 上传题目测试点
     *
     * @param problemId 题目ID
     */
    @PostMapping(value = "/case/{problemId}")
    public Result pushProblemCase(Long problemId, @RequestParam("image") MultipartFile multipartFile){
        return Result.success(problemService.pushProblemCase(problemId, multipartFile));
    }

    /**
     * 删除评论
     *
     * @param commentDTO 评论参数
     */
    @DeleteMapping(value = "/comment/delete")
    public Result deleteComment(@RequestBody CommentDTO commentDTO) {
        return Result.success(problemService.deleteComment(Long.parseLong(commentDTO.getId())));
    }

}
