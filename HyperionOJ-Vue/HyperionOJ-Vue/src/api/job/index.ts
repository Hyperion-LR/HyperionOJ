import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { PageParam } from "../pageParam/types";
import { JobInfo, JobActionInfo } from "./types";

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
export function getJobDetail(jobId: string): AxiosPromise<Result<JobInfo>> {
    return request({
        url: `/job/${jobId}`,
        method: "get"
    });
}

/**
 * 获取题目详情
 * @param jobId 题目id
 * @returns 题目详情信息
 */
export function createJob(jobInfo: JobInfo): AxiosPromise<Result<JobInfo>> {
    return request({
        url: `/job/create`,
        method: "post",
        data: jobInfo
    });
}

/**
 * 删除题目
 * @param jobId 题目id
 * @returns 题目详情信息
 */
export function deleteJob(jobId: string): AxiosPromise<Result<JobInfo>> {
    return request({
        url: `/job/delete/${jobId}`,
        method: "delete"
    });
}

/**
 * 上传jar文件
 * @param jobId jobId
 * @returns 题目详情信息
 */
export function uploadJar(jobId: string, fromData:FormData): AxiosPromise<Result<JobInfo>> {
    return request({
        url: `/job/resource/${jobId}`,
        method: "post",
        data: fromData
    });
}

/**
 * 保存JOB
 * @param jobId 题目id
 * @returns 题目详情信息
 */
export function saveJob(jobInfo: JobInfo): AxiosPromise<Result<JobInfo>> {
    return request({
        url: `/job/update`,
        method: "post",
        data: jobInfo
    });
}

/**
 * 开始JOB
 * @param jobId 题目id
 * @returns 题目详情信息
 */
export function actionJob(jobActionInfo: JobActionInfo): AxiosPromise<Result<JobInfo>> {
    return request({
        url: `/job/action`,
        method: "post",
        data: jobActionInfo
    });
}