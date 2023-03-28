import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { getCodeParam } from "./types";

/**
 * 用户登录
 * @param loginParam 登录信息
 * @returns Token
 */
export function getCode(getCodeParam: getCodeParam): AxiosPromise<Result<string>> {
  return request({
    url: "/user/mail/code",
    method: "post",
    data: getCodeParam,
  });
}