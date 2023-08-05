package com.wpay.common.global.functions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.function.Function;


public class DataFunctions {

    private DataFunctions(){ }


    public static Function<Date, Long> makeSrlno = (date) -> {
        Random random = new Random();
        String result = new StringBuilder()
                .append(random.nextInt(9))
                .append((new SimpleDateFormat("HHmmssSSS")).format(date))
                .toString();
        return Long.valueOf(result);
    };

    public static Function<String, String> getIdcDvdCd = (serverName) -> {
        serverName = serverName.toLowerCase();
        if(serverName.indexOf("ks") == 0) return "NW";
        if(serverName.indexOf("fc") == 0) return "WP";
        if(serverName.indexOf("stg") == 0) return "ST";
        if(serverName.indexOf("dev") == 0) return "DE";
        if(serverName.indexOf("localhost") == 0) return "LO";
        return "UN"; // unknown server name
    };

    public static Function<Integer, String> randomStr = (length) -> {
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            switch(choice) {
                case 0: str.append((char)(random.nextInt(25)+97)); break;
                case 1: str.append((char)(random.nextInt(25) +65)); break;
                case 2: str.append((char)(random.nextInt(10) +48)); break;
                default:
            }
        }
        return str.toString();
    };
}
