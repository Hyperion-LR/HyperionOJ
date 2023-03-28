
import axios, { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from "axios";
import { ElMessage } from "element-plus";
import { getToken } from "./token";

const requests = axios.create({
  baseURL: "/api",
  timeout: 10000,
  // 请求头
  // headers: {
  //   "Content-Type": "application/json;charset=UTF-8",
  // },
});

// 请求拦截器
requests.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 请求带token
    if (getToken()) {
      config.headers["User-Token"] = getToken();
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// 配置响应拦截器
requests.interceptors.response.use(
  (response: AxiosResponse) => {
    switch (response.data.code) {
      case 200:

        ElMessage({
          message: response.data.msg,
          type: 'success',
        })
        break;
      case 500:
        ElMessage.error(response.data.msg)
        break;
    }
    return response;
  },
  (error: AxiosError) => {
    let { message } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    } else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substring(message.length - 3) + "异常";
    }
    return Promise.reject(error);
  }
);

// 对外暴露
export default requests;
