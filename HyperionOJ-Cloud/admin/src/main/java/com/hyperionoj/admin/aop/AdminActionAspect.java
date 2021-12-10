package com.hyperionoj.admin.aop;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.admin.dao.mapper.AdminActionMapper;
import com.hyperionoj.admin.dao.pojo.AdminAction;
import com.hyperionoj.admin.vo.AdminVo;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

import static com.hyperionoj.common.constants.Constants.SUCCESS_CODE;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Component
@Aspect
@Slf4j
public class AdminActionAspect {

    @Resource
    private AdminActionMapper adminActionMapper;

    @Pointcut("@annotation(com.hyperionoj.admin.aop.AdminActionAnnotation)")
    public void pt() {
    }

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        adminActionMapper.insert(getAction(joinPoint, System.currentTimeMillis(), result));
        return result;
    }

    private AdminAction getAction(ProceedingJoinPoint joinPoint, long time, Object result) {
        AdminVo admin = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), AdminVo.class);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AdminActionAnnotation adminActionAnnotation = method.getAnnotation(AdminActionAnnotation.class);
        AdminAction adminAction = new AdminAction();
        adminAction.setAdminAction(adminActionAnnotation.url());
        adminAction.setActionTime(time);
        adminAction.setAdminId(admin.getId());
        if (((Result) result).getCode() == SUCCESS_CODE) {
            adminAction.setActionStatus(0);
        } else {
            adminAction.setActionStatus(1);
        }
        log.info(adminAction.toString());
        return adminAction;
    }


}
