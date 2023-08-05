package com.wpay.common.global.functions;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public final class PrivacyFunctions {
    private PrivacyFunctions() { }

    /**
     * <pre>
     * 성별 코드 값으로 출생 년도 앞 자리 확인
     * </pre>
     */
    public static Function<String, String> findBirthdayFirstYY = (arg) -> {
        final List<String> arr01 = Arrays.asList("0", "9");
        final List<String> arr02 = Arrays.asList("1","2","5","6");
        final List<String> arr03 = Arrays.asList("3","4","7","8");
        if(arr01.contains(arg)) { return "18"; }
        if(arr02.contains(arg)) { return "19"; }
        if(arr03.contains(arg)) { return "20"; }
        throw new CustomException(ErrorCode.HTTP_STATUS_400, "주민등록번호 7번째 자리와 매칭 되는 출생년도 앞 2자리를 찾지 못 했습니다.");
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
        final List<String> inners = Arrays.asList("0","1","2","3","4");
        final List<String> outers = Arrays.asList("5","6","7","8");
        if(inners.contains(arg)) { return "N"; }
        if(outers.contains(arg)) { return "Y"; }
        throw new CustomException(ErrorCode.HTTP_STATUS_400, "주민등록번호 7번째 자리로 내외국인 구분을 하지 못 했습니다.");
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
        if ("M".equals(gender) && "N".equals(foreigner))
            return "19".equals(birth) ? "1" : ("20".equals(birth) ? "3" : "");
        if ("M".equals(gender) && "Y".equals(foreigner))
            return "19".equals(birth) ? "5" : ("20".equals(birth) ? "7" : "");
        if ("F".equals(gender) && "N".equals(foreigner))
            return "19".equals(birth) ? "2" : ("20".equals(birth) ? "4" : "");
        if ("F".equals(gender) && "Y".equals(foreigner))
            return "19".equals(birth) ? "6" : ("20".equals(birth) ? "8" : "");
        throw new CustomException(ErrorCode.HTTP_STATUS_400, "성별 코드, 내외국인 여부, 생년월일 정보로 주민등록번호 7번째 자리 검증에 실패 하였습니다.");
    };
}
