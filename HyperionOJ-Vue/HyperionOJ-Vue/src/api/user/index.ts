
import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { UserInfo } from "./types";

/**
 * 获取用户信息
 * @param data 登录信息
 * @returns Token
 */
export function getUserInfo(userId: string): AxiosPromise<Result<UserInfo>> {
    return request({
      url: `/user/find/${userId}`,
      method: "get",
    });
  }