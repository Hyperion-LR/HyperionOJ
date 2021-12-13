package com.hyperionoj.admin.aop;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.admin.dao.mapper.AdminActionMapper;
import com.hyperionoj.admin.dao.pojo.AdminAction;
import com.hyperionoj.admin.vo.AdminVo;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class PermissionAspect {

    @Resource
    private AdminActionMapper adminActionMapper;

    @Pointcut("@annotation(com.hyperionoj.admin.aop.PermissionAnnotation)")
    public void pt() {
    }

    @Around("pt()")
    public Object action(ProceedingJoinPoint joinPoint) throws Throwable {
        AdminVo admin = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), AdminVo.class);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PermissionAnnotation permissionAnnotation = method.getAnnotation(PermissionAnnotation.class);
        boolean isLevel = checkLevel(permissionAnnotation, admin);
        Object result;
        if (isLevel) {
            result = joinPoint.proceed();
        } else {
            result = Result.fail(ErrorCode.NO_PERMISSION);
        }
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        StringBuilder url = new StringBuilder();
        for (String path : requestMapping.value()) {
            url.append(path);
        }
        adminActionMapper.insert(getAction(admin, url.toString(), result));
        return result;
    }

    private boolean checkLevel(PermissionAnnotation permissionAnnotation, AdminVo admin) {
        return admin.getPermissionLevel() <= permissionAnnotation.level();
    }

    private AdminAction getAction(AdminVo admin, String url, Object result) {
        AdminAction adminAction = new AdminAction();
        adminAction.setAdminAction(url);
        adminAction.setActionTime(System.currentTimeMillis());
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
