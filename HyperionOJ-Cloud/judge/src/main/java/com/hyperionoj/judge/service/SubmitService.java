package com.hyperionoj.judge.service;

import com.hyperionoj.judge.vo.RunResult;
import com.hyperionoj.judge.vo.SubmitVo;

/**
 * 提交代码服务
 *
 * @author Hyperion
 * @date 2021/12/7
 */
public interface SubmitService {

    /**
     * 提交代码
     *
     * @param submit 代码参数
     * @return 代码判题结果
     */
    RunResult submit(SubmitVo submit) throws Exception;
}
