package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 作业开发空间
 * @author Hyperion
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job_working")
public class JobWorkingPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作业ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 作业id
     */
    private Long jobId;

    /**
     * 作业类型，包括JAR、SQL
     */
    private String type;

    private Integer tmSlot;

    private Integer jmMem;

    private Integer tmMem;

    private Integer parallelism;

    /**
     * Flink里对应JobID
     */
    private String flinkId;

    /**
     * JAR模式用户JAR的地址
     */
    private String jarName;

    /**
     * 作业提交运行入口方法
     */
    private String mainClass;

    /**
     * 用户SQL
     */
    @TableField("user_sql")
    private String userSql;

    /**
     * 作业提交运行参数
     */
    private String mainArgs;

    private String applicationId;


}
