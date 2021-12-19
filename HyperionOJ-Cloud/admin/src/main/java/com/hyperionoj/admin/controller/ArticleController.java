package com.hyperionoj.admin.controller;

import com.hyperionoj.admin.aop.PermissionAnnotation;
import com.hyperionoj.common.feign.AdminPageClients;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.params.ArticleParam;
import com.hyperionoj.common.vo.params.CommentParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/19
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    @Resource
    private AdminPageClients adminPageClients;

    /**
     * 删除文章
     *
     * @param articleParam 文章参数
     * @return 结果
     */
    @PermissionAnnotation(level = 2)
    @PostMapping("/delete/article")
    public Result deleteArticle(@RequestBody ArticleParam articleParam) {
        return adminPageClients.deleteArticle(articleParam);
    }

    /**
     * 删除评论
     *
     * @param commentParam 评论参数
     * @return 结果
     */
    @PermissionAnnotation(level = 2)
    @PostMapping("/delete/comment")
    public Result deleteComment(@RequestBody CommentParam commentParam) {
        return adminPageClients.deleteComment(commentParam);
    }

}
