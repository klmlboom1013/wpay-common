package com.wpay.common.global.crypto;

import org.junit.jupiter.api.Test;

class CryptoAES256Test {

    @Test
    void encrypt() {
        String str = "test wpay";
        String result = CryptoAES256.getInstance().encrypt(str);
        System.out.println("--------------------------");
        System.out.println("encrypt result = " + result);
        System.out.println("--------------------------");
    }

    @Test
    void decrypt() {
        String str = "d3BheWNvcmVtb2R1bGUwMF4v4FMCF4HEYZjEjoKxMugnulS73eHjdlr+Ljl0p9KMLmnf0GOJgyctNk+M4A+BIxe6x2098vcmQWL9KeoUM6q1BF149DpCQ5suISlaNFoM/gi+kp9FcnyzoHoUFzgHrxUwkzHS5Oye1u5sTgTb2laqifwa0XDROqod/Ar1CMtiAbdUXeUMHXK4ZGR9rcU9ww==";
        String result = CryptoAES256.getInstance().decrypt(str);
        System.out.println("--------------------------");
        System.out.println("decrypt result = " + result);
        System.out.println("--------------------------");
    }
}