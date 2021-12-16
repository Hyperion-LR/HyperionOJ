package com.hyperionoj.common.feign;

import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.common.vo.ProblemCategoryVo;
import com.hyperionoj.common.vo.ProblemVo;
import com.hyperionoj.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    Result addProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo);

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
     * 删除评论
     *
     * @param commentVo 评论参数
     * @return result
     */
    @RequestMapping(value = "/problem/admin/delete/comment", method = RequestMethod.POST, consumes = "application/json")
    Result deleteComment(@RequestBody CommentVo commentVo);

}
