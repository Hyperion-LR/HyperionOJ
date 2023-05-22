import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { PageParam } from "../pageParam/types";
import { CategoryInfo, ProblemInfo, SubmitInfo, SubmitResultInfo } from "./types";


/**
 * 获取题目数量
 * @returns 题目数量
 */
export function getProbelmNumber(): AxiosPromise<Result<number>> {
    return request({
        url: `/problem/count`,
        method: "get",
    });
}

/**
 * 获取题目列表
 * @returns 题目列表
 */
export function getProbelmList(pageParam: PageParam): AxiosPromise<Result<ProblemInfo[]>> {
    return request({
        url: `/problem/list?`,
        method: "get",
        params: pageParam
    });
}

/**
 * 获取分类列表
 * @returns 题目列表
 */
export function getCategoryList(): AxiosPromise<Result<CategoryInfo[]>> {
    return request({
        url: `/problem/category`,
        method: "get"
    });
}

/**
 * 获取题目详情
 * @param problemId 题目id
 * @returns 题目详情信息
 */
export function getProblemDetail(problemId: string): AxiosPromise<Result<ProblemInfo>>{
    return request({
        url: `/problem/${problemId}`,
        method: "get"
    });
}

/**
 * 获取提交列表
 * @param problemId 题目id
 * @returns 题目详情信息
 */
export function getSubmitList(pageParam: PageParam): AxiosPromise<Result<SubmitInfo[]>>{
    return request({
        url: `/problem/submits`,
        method: "get",
        params: pageParam
    });
}

/**
 * 提交代码
 * @param problemId 题目id
 * @returns 题目详情信息
 */
export function submit(submitInfo: SubmitInfo): AxiosPromise<Result<SubmitResultInfo>>{
    return request({
        url: `/problem/submit`,
        method: "post",
        data: submitInfo
    });
}

/**
 * 获取提交详情
 * @param problemId 题目id
 * @returns 题目详情信息
 */
export function getSubmit(submitId: string): AxiosPromise<Result<SubmitInfo>>{
    return request({
        url: `/problem/submit/${submitId}`,
        method: "get"
    });
}

