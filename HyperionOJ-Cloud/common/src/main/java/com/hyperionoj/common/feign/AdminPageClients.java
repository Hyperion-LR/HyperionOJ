package com.hyperionoj.common.feign;

import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.common.vo.ProblemCategoryVo;
import com.hyperionoj.common.vo.ProblemVo;
import com.hyperionoj.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Hyperion
 * @date 2021/12/13
 */
@FeignClient("oss")
public interface AdminPageClients {

    /**
     * 添加题目分类
     *
     * @param problemCategoryVo 分类信息
     * @return 分类情况
     */
    @PostMapping("/problem/admin/add/problem/category")
    Result addProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo);

    /**
     * 删除题目分类
     *
     * @param problemCategoryVo 分类参数
     * @return null
     */
    @PostMapping("/problem/admin/delete/problem/category")
    Result deleteProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo);

    /**
     * 添加题目
     *
     * @param problemVo 题目对象
     * @return 新加的题目
     */
    @PostMapping("/problem/admin/add/problem")
    Result addProblem(@RequestBody ProblemVo problemVo);

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     * @return result
     */
    @PostMapping("/problem/admin/update/problem")
    Result updateProblem(@RequestBody ProblemVo problemVo);

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     * @return result
     */
    @PostMapping("/problem/admin/delete/problem")
    Result deleteProblem(@RequestBody ProblemVo problemVo);

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     * @return result
     */
    @PostMapping("/problem/admin/delete/comment")
    Result deleteComment(@RequestBody CommentVo commentVo);

}
