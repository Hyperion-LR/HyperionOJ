import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { PageParam } from "../pageParam/types";
import { ProblemInfo } from "./types";


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
        url: `/problem/list`,
        method: "get",
        data: pageParam
    });
}