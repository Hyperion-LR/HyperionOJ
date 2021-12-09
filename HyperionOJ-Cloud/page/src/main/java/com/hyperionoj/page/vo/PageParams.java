package com.hyperionoj.page.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@Data
public class PageParams {

    private Integer page = 1;

    private Integer pageSize = 10;

    private Integer level;

    private Long categoryId;

}
