package com.wpay.common.global.functions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class DateFunctions {
    /**
     * result : yyyy-MM-dd HH:mm:ss.SSS
     */
    public static Function<Date, String> getTimestampMilliSecond =
            (date -> (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(date));

    /**
     * <pre>
     * result : String[2]
     * [0] = yyyyMMdd
     * [1] = HHmmss
     * </pre>
     */
    public static Function<Date, String[]> getDateAndTime =
            (date -> (new SimpleDateFormat("yyyyMMdd-HHmmss")).format(date).split("-"));
}
