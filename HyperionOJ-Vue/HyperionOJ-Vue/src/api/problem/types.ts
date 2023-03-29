/**
 * 题目信息
 */
export interface ProblemInfo {
    id: string,
    title: string,
    problemLevel: number,
    acNumber: number,
    submitNumber: number,
    commentNumber: number,
    caseNumber: number,
    runMemory: number,
    runTime: number,
    problemBody: string,
    problemBodyHtml: string,
    category: CategoryInfo,
    tags: TagInfo[]
}

export interface TagInfo {
    id: string,
    tagName: string
}

export interface CategoryInfo {
    id: string,
    categoryName: string,
    description: string
}

export interface SubmitInfo {
    id: string,
    authorId: string,
    problemId: string,
    codeLang: string,
    codeBody: string,
    runTime: string,
    runMemory: string,
    caseNumber: string,
    verdict: string,
    createTime: string
}