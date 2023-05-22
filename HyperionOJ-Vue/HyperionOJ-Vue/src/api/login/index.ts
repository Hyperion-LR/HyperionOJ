import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { LoginParam } from "./types";

/**
 * 用户登录
 * @param loginParam 登录信息
 * @returns Token
 */
export function login(loginParam: LoginParam): AxiosPromise<Result<string>> {
  return request({
    url: "/user/login",
    method: "post",
    data: loginParam,
  });
}