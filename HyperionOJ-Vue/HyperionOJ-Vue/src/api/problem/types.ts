/**
 * 题目信息
 */
export interface ProblemInfo {
    id:string;
    title:string,
    problemLevel: number,
    acNumber: number,
    submitNumber: number,
    commentNumber: number,
    caseNumber: number,
    runMemory: number,
    runTime: number,
    problemBody: string,
    problemBodyHtml: string,
    category: number,
    tags: number
}