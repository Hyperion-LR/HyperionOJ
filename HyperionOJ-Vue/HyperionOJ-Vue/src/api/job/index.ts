import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { PageParam } from "../pageParam/types";
import { JobInfo } from "./types";

/**
 * 获取题目列表
 * @returns 题目列表
 */
export function getJobList(pageParam: PageParam): AxiosPromise<Result<JobInfo[]>> {
    return request({
        url: `/job/list?`,
        method: "get",
        params: pageParam
    });
}

/**
 * 获取题目详情
 * @param jobId 题目id
 * @returns 题目详情信息
 */
export function getJobDetail(jobId: string): AxiosPromise<Result<JobInfo>>{
    return request({
        url: `/problem/${jobId}`,
        method: "get"
    });
}