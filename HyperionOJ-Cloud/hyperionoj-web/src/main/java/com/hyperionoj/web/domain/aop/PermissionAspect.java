package com.hyperionoj.web.domain.aop;


import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Component
@Aspect
@Slf4j
public class PermissionAspect {
/*
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
*/
}
