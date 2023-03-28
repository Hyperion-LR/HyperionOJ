
/**
 * 返回结果
 */
export interface Result<T> {
    /**
     * 状态码
     */
    code:number;
    /**
     * 返回数据
     */
    data:T;
    /**
     * 提示信息
     */
    msg:string;
}