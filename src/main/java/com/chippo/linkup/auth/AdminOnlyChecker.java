package com.chippo.linkup.auth;

import com.chippo.linkup.auth.domain.Accessor;
import com.chippo.linkup.global.exception.AuthException;
import com.chippo.linkup.global.exception.ExceptionCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AdminOnlyChecker {

    //아래 어노테이션이 실행되기 전에 검사 하는 로직
    @Before("@annotation(com.chippo.linkup.auth.AdminOnly)")
    public void check(final JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(Accessor.class::isInstance)
                .map(Accessor.class::cast)
                .filter(Accessor::isAdmin)
                .findFirst()
                .orElseThrow(() -> new AuthException(ExceptionCode.INVALID_ADMIN_AUTHORITY));
    }
}
