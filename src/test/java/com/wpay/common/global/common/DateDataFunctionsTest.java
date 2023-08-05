package com.wpay.common.global.common;

import com.wpay.common.global.functions.DateFunctions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class DateDataFunctionsTest {

    @Test
    public void tests() {
        System.out.println("=================");
        System.out.println(DateFunctions.getTimestampMilliSecond.apply(new Date()));
        System.out.println("=================");
        String[] result = DateFunctions.getDateAndTime.apply(new Date());
        System.out.println("getDateAndTime result size : " + result.length);
        System.out.println("getDateAndTime result[0] : " + result[0]);
        System.out.println("getDateAndTime result[1] : " + result[1]);
        System.out.println("=================");
    }
}