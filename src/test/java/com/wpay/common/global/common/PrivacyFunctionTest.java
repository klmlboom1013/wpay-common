package com.wpay.common.global.common;

import org.junit.jupiter.api.Test;

class PrivacyFunctionTest {

    @Test
    public void test() {
        String result;
        System.out.println("=================");
        result = PrivacyFunction.findBirthdayFirstYY.apply("1");
        System.out.println("findBirthdayFirstYY = " + result + " ["+("19".equals(result))+"]");
        result = PrivacyFunction.findBirthdayFirstYY.apply("4");
        System.out.println("findBirthdayFirstYY = " + result + " ["+("20".equals(result))+"]");
        System.out.println("=================");
        result  = PrivacyFunction.findGenderCode.apply(1);
        System.out.println("findGenderCode = " + result + " ["+("M".equals(result))+"]");
        result  = PrivacyFunction.findGenderCode.apply(2);
        System.out.println("findGenderCode = " + result + " ["+("F".equals(result))+"]");
        System.out.println("=================");
        result  = PrivacyFunction.findForeignerYN.apply("1");
        System.out.println("findForeignerYN = " + result + " ["+("N".equals(result))+"]");
        result  = PrivacyFunction.findForeignerYN.apply("6");
        System.out.println("findForeignerYN = " + result + " ["+("Y".equals(result))+"]");
        System.out.println("=================");
        result  = PrivacyFunction.findJuminIdSection2to1.apply(new String[]{"M","N","19"});
        System.out.println("findJuminIdSection2to1 = " + result + " ["+("1".equals(result))+"]");
        result  = PrivacyFunction.findJuminIdSection2to1.apply(new String[]{"F","Y","20"});
        System.out.println("findJuminIdSection2to1 = " + result + " ["+("8".equals(result))+"]");
        System.out.println("=================");
    }
}