package com.hyperionoj.common.vo.params;

import com.hyperionoj.common.vo.CategoryVo;
import com.hyperionoj.common.vo.TagVo;
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

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private Integer isSolution;

    private String problemId;

}
