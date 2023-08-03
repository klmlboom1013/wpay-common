package com.wpay.common.global.common;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public final class PrivacyFunction {
    private PrivacyFunction() { }

    /**
     * <pre>
     * 성별 코드 값으로 출생 년도 앞 자리 확인
     * </pre>
     */
    public static Function<String, String> findBirthdayFirstYY = (arg) -> {
        try {
            final List<String> arr01 = Arrays.asList("0", "9");
            final String arr11 = arr01.stream().filter(s -> s.equals(arg)).findAny().get();
            if (Strings.isNotBlank(arr11)) { return "18"; }
        } catch (NoSuchElementException ignore) { }
        try {
            final List<String> arr02 = Arrays.asList("1","2","5","6");
            final String arr12 = arr02.stream().filter(s -> s.equals(arg)).findAny().get();
            if(Strings.isNotBlank(arr12)) { return "19"; }
        } catch (NoSuchElementException ignore) { }
        try {
            final List<String> arr03 = Arrays.asList("3","4","7","8");
            final String arr13 = arr03.stream().filter(s -> s.equals(arg)).findAny().get();
            if(Strings.isNotBlank(arr13)) { return "20"; }
        } catch (NoSuchElementException ignore) { }
        throw new RuntimeException("입력된 조건으로 출생 년도 앞 2자리를 찾을 수 없습니다. [arg:"+arg+"]");
    };

    /**
     * <pre>
     * 성별 코드 값 확인
     * 주민번호 뒤 1자리 성별 값이 2로 나눈 나머지가 0이면 F
     * </pre>
     */
    public static Function<Integer, String> findGenderCode = arg -> (arg%2==0) ? "F" : "M";
    
    /**
     * <pre>
     * 주민번호 뒤 1자리 성별 코드로 외국인 여부 확인
     * 외국인 여부 - Y:외국인 / N:내국인
     * </pre>
     */
    public static Function<String,String> findForeignerYN = (arg) -> {
        try {
            final List<String> inners = Arrays.asList("0","1","2","3","4","5");
            final String isIns = inners.stream().filter(s -> s.equals(arg)).findAny().get();
            if(Strings.isNotBlank(isIns)) { return "N"; }
        } catch (NoSuchElementException ignore) { }
        try {
            final List<String> outers = Arrays.asList("5","6","7","8");
            final String isOuts = outers.stream().filter(s -> s.equals(arg)).findAny().get();
            if(Strings.isNotBlank(isOuts)) { return "Y"; }
        } catch (NoSuchElementException ignore) { }
        throw new RuntimeException("입력된 조건으로 외국인 여부를 알 수 없습니다. [arg:"+arg+"]");
    };

    /**
     * <pre>
     * 주민번호 뒤 1자리(성별) 찾기.
     * 입력 값:
     * [0]: 성별 코드 - M:남자 / F:여자
     * [1]: 외국인 여부 - Y:외국인 / N:내국인
     * [2]: 생년월일 중 년 2자리
     * </pre>
     */
    public static Function<String[], String> findJuminIdSection2to1 = (args) -> {
        final String gender = args[0];
        final String foreigner = args[1];
        final String birth = args[2];
        if ("M".equals(gender) && "N".equals(foreigner)) {
            return "19".equals(birth) ? "1" : ("20".equals(birth) ? "3" : "");
        } else if ("M".equals(gender) && "Y".equals(foreigner)) {
            return "19".equals(birth) ? "5" : ("20".equals(birth) ? "7" : "");
        } else if ("F".equals(gender) && "N".equals(foreigner)) {
            return "19".equals(birth) ? "2" : ("20".equals(birth) ? "4" : "");
        } else if ("F".equals(gender) && "Y".equals(foreigner)) {
            return "19".equals(birth) ? "6" : ("20".equals(birth) ? "8" : "");
        }
        throw new RuntimeException("조건에 맞는 성별 코드 값을 찾을 수 없습니다. [args[0]:"+args[0]+"][args[1]:"+args[1]+"][args[2]:"+args[2]+"]");
    };
}
