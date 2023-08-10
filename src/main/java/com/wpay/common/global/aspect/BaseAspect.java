package com.wpay.common.global.aspect;

import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.webclient.CustomWebClientRequestException;
import com.wpay.common.global.exception.webclient.CustomWebClientResponseException;
import com.wpay.common.global.exception.webclient.CustomWebClientTimeoutException;
import com.wpay.common.global.functions.DataFunctions;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.SelfCrypto;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public abstract class BaseAspect {

    public abstract void before(JoinPoint joinPoint);
    public abstract void after(JoinPoint joinPoint);
    public abstract void afterReturning(JoinPoint joinPoint, Object result);
    public abstract void afterThrowing(JoinPoint joinPoint, Throwable e);

    /**
     * <pre>
     *     데이터 암/복호화 후 Validate 검증을 진행 한다.
     *     SelfValidating 객체를 상속 받은 domain만 프로세스 진행이 가능 하다.
     * </pre>
     */
    protected void validateCryptoSelf(JoinPoint joinPoint, boolean cryptoFlag){
        log.debug("DTO Validation 검증 및 데이터 암복호화를 시작 합니다.");
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfValidating 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof SelfValidating))) return false;
            isPlay.set(true);
            final SelfValidating<?> selfValidating = (SelfValidating<?>) o;
            /* validation Crypto check */
            if(cryptoFlag)
                selfValidating.validateCryptoSelf();
            else
                selfValidating.validateSelf();
            log.info("Validation check success!! \n[{}]", o.toString());
            return true;
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("DTO Validation 검증 및 데이터 암복호화 진행 대상이 없습니다.");
    }

    /**
     *<pre>
     *     데이터 암/복호화를 진행 한다.
     *     SelfCrypto가 상속 된 domain만 프로세스 진행이 가능 하다.
     *</pre>
     */
    protected void cryptoSelf(JoinPoint joinPoint){
        log.debug("DTO 데이터 암복호화를 시작 합니다.");
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof SelfCrypto))) return false;
            isPlay.set(true);
            final SelfCrypto selfCrypto = (SelfCrypto) o;
            /* validation Crypto check */
            selfCrypto.resetFieldDataCrypto();
            log.info("Crypto success!! \n[{}]", o.toString());
            return true;
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("DTO 데이터 암복호화 진행 대상이 없습니다.");
    }

    /**
     * <pre>
     *     데이터 암/복호화 후 Validate 검증을 진행 한다.
     *     SelfValidating 객체를 상속 받은 domain만 프로세스 진행이 가능 하다.
     *     추가로, HttpServletRequest Header의 content-type 을 검증 한다.
     * </pre>
     */
    protected void baseCommandValidateCryptoSelf (JoinPoint joinPoint, HttpServletRequest request) {
        log.debug("http header 미디어 타입을 검증을 시작 합니다.");
        AtomicReference<String> contentType = new AtomicReference<>();
        request.getHeaderNames().asIterator().forEachRemaining(key -> {
            if(key.equalsIgnoreCase("content-type")){
                contentType.set(request.getHeader(key));
            }
        });
        log.debug(">>> http request header: contentType : {}", contentType.get());
        if(Strings.isBlank(contentType.get()) || Boolean.FALSE.equals(contentType.get().toLowerCase().contains("application/json"))){
            throw new CustomException(
                    CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_415).build());
        }

        log.debug("CommandDTO Validation 검증 및 데이터 암복호화를 시작 합니다.");
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof BaseCommand))) return false;
            isPlay.set(true);
            log.info("RequestBody Command : \n[{}]", o.toString());
            final BaseCommand<?> baseCommand = (BaseCommand<?>) o;
            /* API Call Version 확인 */
            baseCommand.checkVersion(request.getRequestURI().split("/")[2]);
            /* set serverName */
            baseCommand.setIdcDvdCd(DataFunctions.getIdcDvdCd.apply(request.getServerName()));
            /* validation check */
            baseCommand.validateCryptoSelf();
            log.info("Validation check success!! \n[{}]", o.toString());
            return true;
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("CommandDTO Validation 검증 및 데이터 암복호화 진행 대상이 없습니다.");
    }

    /**
     * <pre>
     *     ResponseBody Dto 내 setPath 필드 값을 해당 API URI로 세팅 한다.
     * </pre>
     */
    protected void resultSetUriPath(Object result, HttpServletRequest request) {
        if(Objects.isNull(result) || Boolean.FALSE.equals(result instanceof ResponseEntity)) {
            log.warn("Return Object 는 ResponseEntity 가 아니 므로 ResponseEntity.body.path 세팅이 불가능 합니다.");
            return;
        }

        if(Objects.isNull(((ResponseEntity<?>) result).getBody())) {
            log.warn("ResponseEntity 또는 ResponseEntity.body 가 Null 이므로 path 값 세팅이 불가능 합니다.");
            return;
        }

        final ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        final Object body = responseEntity.getBody();
        log.debug("ResponseEntity.body 에 path 세팅을 시작 합니다. [{}]", body.getClass().getSimpleName());
        // Body 에 path 세팅
        final Class<?> clazz = body.getClass();
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(clazz.getDeclaredMethods()).iterator().forEachRemaining(f ->{
            if("setPath".equals(f.getName())) {
                isPlay.set(true);
                try {
                    f.invoke(body, request.getRequestURI());
                    log.info("ResultDTO API URI Path 세팅 완료. [{}]", body.toString());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    isPlay.set(false);
                    log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());
                    throw new CustomException(
                            CustomExceptionData.builder()
                                    .errorCode(ErrorCode.HTTP_STATUS_500)
                                    .message("ResponseEntity.body 에 path 세팅 중 오류가 발생 했습니다.")
                                    .e(e)
                                    .build());
                }
            }
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("ResponseEntity.body 에 path 세팅 대상 method 가 없습니다. [{}]", result.getClass().getSimpleName());
    }

    /**
     * <pre>
     *      Adapter out external 프로세스 처리 중 Exception 발생 감지 Aspect 공통 처리 로직
     *      대외계 통신 및 응답 오류 발생 건에 대한 HttpStatus 코드 지정.
     *      외 나머지 Exception은 CustomException에 담는다.
     * </pre>
     */
    protected void afterThrowingForExternal (JoinPoint joinPoint, Throwable e) {
        final SelfValidating<?>[] activity = { null };
        Arrays.stream(joinPoint.getArgs()).forEach(o -> {
            if((o instanceof SelfValidating<?>) && Objects.isNull(activity[0])) { activity[0] = (SelfValidating<?>)o; }
        });

        String wtid, jnoffcId;
        try {
            Class<?> clazz = activity.getClass();
            Method getWtid = clazz.getMethod("getWtid");
            Method getJnoffcId = clazz.getMethod("getJnoffcId");
            wtid = (String) getWtid.invoke(activity);
            jnoffcId = (String) getJnoffcId.invoke(activity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new CustomException(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_500).e(ex).build());
        }

        if (e instanceof CustomWebClientRequestException) {
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_501).e(e).wtid(wtid).jnoffcId(jnoffcId)
                    .data(((CustomWebClientRequestException) e).getMapper()).build());
        } else if (e instanceof CustomWebClientResponseException) {
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_503).e(e).wtid(wtid).jnoffcId(jnoffcId)
                    .data(((CustomWebClientResponseException) e).getMapper()).build());
        } else if (e instanceof CustomWebClientTimeoutException) {
            final CustomWebClientTimeoutException ex = (CustomWebClientTimeoutException) e;
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_504).e(ex).wtid(wtid).jnoffcId(jnoffcId)
                    .data(((CustomWebClientTimeoutException) e).getMapper()).build());
        } else if(Boolean.FALSE.equals(e instanceof CustomException)) {
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_500).e(e).wtid(wtid).jnoffcId(jnoffcId).build());
        }
    }

    /**
     * <pre>
     *     API 프로세스 처리 완료 후 Client로 Return 하기 전
     *     ResponseBody 내 암호화 대상 필드가 있다면 암호화를 진행 한다.
     * </pre>
     */
    protected void afterReturningEncryptResponseBody(Object result, HttpServletRequest request) {
        /* result가 Null 이거나 타입이 ResponseEntity이 아니면 하위 로직 PASS */
        if(Objects.isNull(result) || Boolean.FALSE.equals((result instanceof ResponseEntity))) { return; }

        /* api uri path 세팅 */
        this.resultSetUriPath(result, request);

        /* ResponseEntity body 타입이 BaseResponse 맞는지? */
        final ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        if(Objects.isNull(responseEntity.getBody()) || Boolean.FALSE.equals(responseEntity.getBody() instanceof BaseResponse)) { return; }

        /* ResponseEntity body의 Data 객체가 SelfCrypto를 상속 받았는지?? 상속 받았으면 데이터 암호화 진행. */
        final BaseResponse baseResponse = (BaseResponse) responseEntity.getBody();
        if(baseResponse.getData() instanceof SelfCrypto) {
            log.info("BaseResponse.data 암호화 시작 [ASIS: {}]", baseResponse.getData().toString());
            ((SelfCrypto)baseResponse.getData()).resetFieldDataCrypto();
            log.info("BaseResponse.data 암호화 완료 [TOBE: {}]", baseResponse.getData().toString());
        }
    }
}
