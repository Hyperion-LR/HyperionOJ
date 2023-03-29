/**
 * 分页参数
 */
export interface PageParam extends FormData {

    page: number;

    pageSize: number;

    /**
     * 题目难度
     */
    level: number;

    /**
     * 分类id
     */
    categoryId: string;

    /**
     * 标签id
     */
    tagId: string;

    /**
     * 题目id
     */
    problemId: string;

    /**
     * 用户名称
     */
    username: string;

    /**
     * 用户id
     */
    authorId: string;

    /**
     * 提交结果
     */
    verdict: string;

    /**
     * 代码语言
     */
    codeLang: string;

    /**
     * 年
     */
    year: string;

    /**
     * 月
     */
    month: string;

}