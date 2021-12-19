package com.hyperionoj.common.feign;

import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.common.vo.ProblemCategoryVo;
import com.hyperionoj.common.vo.ProblemVo;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.params.ArticleParam;
import com.hyperionoj.common.vo.params.CommentParam;
import com.hyperionoj.common.vo.params.SysClassParam;
import com.hyperionoj.common.vo.params.SysHomeworkParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hyperion
 * @date 2021/12/13
 */
@FeignClient("page")
public interface AdminPageClients {

    /**
     * 添加题目分类
     *
     * @param problemCategoryVo 分类信息
     * @return 分类情况
     */
    @RequestMapping(value = "/problem/admin/add/problem/category", method = RequestMethod.POST, consumes = "application/json")
    Result<Object> addProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo);

    /**
     * 删除题目分类
     *
     * @param problemCategoryVo 分类参数
     * @return null
     */
    @RequestMapping(value = "/problem/admin/delete/problem/category", method = RequestMethod.DELETE, consumes = "application/json")
    Result deleteProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo);

    /**
     * 添加题目
     *
     * @param problemVo 题目对象
     * @return 新加的题目
     */
    @RequestMapping(value = "/problem/admin/add/problem", method = RequestMethod.POST, consumes = "application/json")
    Result addProblem(@RequestBody ProblemVo problemVo);

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     * @return result
     */
    @RequestMapping(value = "/problem/admin/update/problem", method = RequestMethod.POST, consumes = "application/json")
    Result updateProblem(@RequestBody ProblemVo problemVo);

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     * @return result
     */
    @RequestMapping(value = "/problem/admin/delete/problem", method = RequestMethod.DELETE, consumes = "application/json")
    Result deleteProblem(@RequestBody ProblemVo problemVo);

    /**
     * 删除题目评论
     *
     * @param commentVo 评论参数
     * @return result
     */
    @RequestMapping(value = "/problem/admin/delete/comment", method = RequestMethod.POST, consumes = "application/json")
    Result deleteComment(@RequestBody CommentVo commentVo);

    /**
     * 删除文章
     *
     * @param articleParam 文章参数
     * @return 结果
     */
    @PostMapping("/article/admin/delete/article")
    Result deleteArticle(@RequestBody ArticleParam articleParam);

    /**
     * 删除评论
     *
     * @param commentParam 评论参数
     * @return 结果
     */
    @PostMapping("/article/admin/delete/comment")
    Result deleteComment(@RequestBody CommentParam commentParam);

    /**
     * 获取课程列表
     *
     * @return 课程列表
     */
    @GetMapping("/article/admin/class")
    Result getClassList();

    /**
     * 获取课程详情
     *
     * @param id 课程id
     * @return 课程详情
     */
    @GetMapping("/article/admin/class/{id}")
    Result getSysClass(@PathVariable("id") Long id);

    /**
     * 创建文章
     *
     * @param classParam 班级参数
     * @return 返回班级
     */
    @PostMapping("/article/admin/create")
    Result createClass(@RequestBody SysClassParam classParam);

    /**
     * 往课程添加学生
     *
     * @param student 学生参数
     * @param classId 课程id
     * @return 是否加入成功
     */
    @PostMapping("/article/admin/add/{id}")
    Result addStudent(@RequestBody SysUser student, @PathVariable("id") Long classId);

    /**
     * 移除学生
     *
     * @param student 学生参数
     * @param classId 课程id
     * @return 一处结果
     */
    @PostMapping("/article/admin/remove/{id}")
    Result removeStudent(@RequestBody SysUser student, @PathVariable("id") Long classId);

    /**
     * 发布作业
     *
     * @param homeworkParam 作业参数
     * @return 作业结果
     */
    @PostMapping("/article/admin/push")
    Result pushHomework(@RequestBody SysHomeworkParam homeworkParam);

    /**
     * 查看作业列表
     *
     * @param classId 课程id
     * @return 作业列表
     */
    @GetMapping("/article/admin/homeworks/{id}")
    Result getHomeworkList(@PathVariable("id") Long classId);

    /**
     * 查看作业详情
     *
     * @param id 作业id
     * @return 作业详情
     */
    @GetMapping("/article/admin/homework/{id}")
    Result getHomeworkId(@PathVariable("id") Long id);

    /**
     * 更新作业
     *
     * @param homeworkParam 作业参数
     * @return 作业详情
     */
    @PostMapping("/article/adminupdate/homework")
    Result updateHomework(@RequestBody SysHomeworkParam homeworkParam);

    /**
     * 删除作业
     *
     * @param homeworkParam 作业参数
     * @return 删除结果
     */
    @DeleteMapping("/article/admindelete/homework")
    Result deleteHomework(@RequestBody SysHomeworkParam homeworkParam);

    /**
     * 查看作业提交情况
     *
     * @param homeworkId 作业id
     * @return 提交列表
     */
    @GetMapping("/article/admin/submits/{id}")
    Result submitList(@PathVariable("id") Long homeworkId);


}
