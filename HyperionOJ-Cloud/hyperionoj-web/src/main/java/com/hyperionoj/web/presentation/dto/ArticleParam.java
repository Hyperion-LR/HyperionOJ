package com.hyperionoj.web.presentation.dto;

import com.hyperionoj.web.presentation.vo.CategoryVO;
import com.hyperionoj.web.presentation.vo.TagVO;
import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
public class ArticleParam {

    private String id;

    private String title;

    private ArticleBodyParam body;

    private CategoryVO category;

    private String summary;

    private List<TagVO> tags;

    private Integer isSolution;

    private String problemId;

}
