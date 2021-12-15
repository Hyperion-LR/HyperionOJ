package com.hyperionoj.page.vo.params;

import com.hyperionoj.page.vo.CategoryVo;
import com.hyperionoj.page.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
public class ArticleParam {

    private String title;

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;
}
