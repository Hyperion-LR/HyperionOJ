package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 作业基本信息
 * @author Hyperion
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job_base")
public class JobBasePO {

    /**
     * 作业ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 作品名
     */
    private String name;

    /**
     * 作业描述
     */
    private String description;

    /**
     * 所有者ID
     */
    private Long ownerId;

    /**
     * 作业状态
     */
    private String status;

    /**
     * 作业开始运行时间
     */
    private Long startTime;

    /**
     * 作业创建时间
     */
    private Long createTime;

    /**
     * cpu使用情况 单位：毫核
     */
    private Integer cpuUsage;

    /**
     * 内存使用情况 单位：MiB
     */
    private Integer memUsage;

    /**
     * Flink WebUI地址
     */
    private String flinkUrl;

    /**
     * 作业对应的grafana监控url路径
     */
    private String monitorUrl;

    /**
     * Ingress外部暴露作业访问地址
     */
    private String outerUrl;

}
