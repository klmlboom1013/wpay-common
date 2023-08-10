package com.wpay.common.global.aspect;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
@Aspect
@Component(value = "baseWebAdapterAspect")
@RequiredArgsConstructor
public class WebAdapterAspect extends BaseAspect {

    private final HttpServletRequest request;

    @Before("execution(* com.wpay..adapter.in.web.*Controller.*Run(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.info("[Before] => {}", joinPoint.getSignature().getName());

        List<BaseCommand<?>> baseCommands = new ArrayList<>();
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof BaseCommand))) return false;
            baseCommands.add((BaseCommand<?>)o);
            return true;
        });

        log.debug("CommandDto class full name: {}", baseCommands.get(0).getClass().getName());

        if(baseCommands.isEmpty()) {
            log.error("해당 API의 내부 정책 오류 입니다. BaseCommand를 상속 받은 RequestBody DTO가 존재 하지 않습니다.");
            throw new CustomException(ErrorCode.HTTP_STATUS_500);
        }
        if(baseCommands.get(0).getClass().getName().indexOf("application.port.in.dto") < 1) {
            log.error("해당 API의 내부 정책 오류 입니다. BaseCommand 구현 DTO 생성 경로 오류 입니다.");
            throw new CustomException(ErrorCode.HTTP_STATUS_500);
        }
        if(baseCommands.get(0).getClass().getSimpleName().indexOf("Command") < 1) {
            log.error("해당 API의 내부 정책 오류 입니다. BaseCommand 구현 DTO의 이름 마지막에 \"Command\" 문자가 포함 되어야 합니다.");
            throw new CustomException(ErrorCode.HTTP_STATUS_500);
        }

        this.baseCommandValidateCryptoSelf(joinPoint, request);
    }

    @After("execution(* com.wpay..adapter.in.web.*Controller.*Run(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.info("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay..adapter.in.web.*Controller.*Run(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(),
                (Objects.nonNull(result)) ? result.toString() : new Object());
        super.afterReturningEncryptResponseBody(result, this.request);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay..adapter.in.web.*Controller.*Run(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
