package com.hyperionoj.web.infrastructure.config;

import com.hyperionoj.web.domain.listener.JobEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @author Hyperion
 * @date 2023/3/16
 */
@Component
public class ListenerConfig {

    private static final Logger log = LoggerFactory.getLogger(ListenerConfig.class);

    @Resource
    private Executor listenerTaskExecutor;

    @Resource
    private JobEventListener jobEventListener;

    @PostConstruct
    private void conf(){
        listenerTaskExecutor.execute(jobEventListener);
        log.info("flink job 心跳开始监听");
    }
}
