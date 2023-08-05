package com.wpay.common.global.common;

import com.wpay.common.global.functions.PrivacyFunctions;
import org.junit.jupiter.api.Test;

class PrivacyFunctionTest {

    @Test
    public void test() {
        String result;
        System.out.println("=================");
        result = PrivacyFunctions.findBirthdayFirstYY.apply("1");
        System.out.println("findBirthdayFirstYY = " + result + " ["+("19".equals(result))+"]");
        result = PrivacyFunctions.findBirthdayFirstYY.apply("4");
        System.out.println("findBirthdayFirstYY = " + result + " ["+("20".equals(result))+"]");
        System.out.println("=================");
        result  = PrivacyFunctions.findGenderCode.apply(1);
        System.out.println("findGenderCode = " + result + " ["+("M".equals(result))+"]");
        result  = PrivacyFunctions.findGenderCode.apply(2);
        System.out.println("findGenderCode = " + result + " ["+("F".equals(result))+"]");
        System.out.println("=================");
        result  = PrivacyFunctions.findForeignerYN.apply("1");
        System.out.println("findForeignerYN = " + result + " ["+("N".equals(result))+"]");
        result  = PrivacyFunctions.findForeignerYN.apply("6");
        System.out.println("findForeignerYN = " + result + " ["+("Y".equals(result))+"]");
        System.out.println("=================");
        result  = PrivacyFunctions.findJuminIdSection2to1.apply(new String[]{"M","N","19"});
        System.out.println("findJuminIdSection2to1 = " + result + " ["+("1".equals(result))+"]");
        result  = PrivacyFunctions.findJuminIdSection2to1.apply(new String[]{"F","Y","20"});
        System.out.println("findJuminIdSection2to1 = " + result + " ["+("8".equals(result))+"]");
        System.out.println("=================");
    }
}