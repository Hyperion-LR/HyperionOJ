package com.hyperionoj.web.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Hyperion
 * @date 2023/3/16
 */
@FeignClient(value = "flink", url = "${flink.url}")
public interface FlinkFeign {

    /**
     * 查询作业列表
     * @return 作业列表
     */
    @GetMapping("/jobs")
    String jobList();

    /**
     * 查询作业详细信息
     * @param jobId flink JobID
     * @return 结果
     */
    @GetMapping("/jobs/{jobId}")
    String jobDetail(@PathVariable("jobId") String jobId);

}
