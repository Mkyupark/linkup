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
public class MasterOnlyChecker {

    @Before("@annotation(com.chippo.linkup.auth.MasterOnly)")
    public void check(final JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(Accessor.class::isInstance)
                .map(Accessor.class::cast)
                .filter(Accessor::isMaster)
                .findFirst()
                .orElseThrow(() -> new AuthException(ExceptionCode.INVALID_ADMIN_AUTHORITY));
    }
}
