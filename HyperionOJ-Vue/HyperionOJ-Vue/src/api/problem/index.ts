import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { PageParam } from "../pageParam/types";
import { CategoryInfo, ProblemInfo } from "./types";


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