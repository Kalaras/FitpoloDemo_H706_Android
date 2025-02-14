package com.fitpolo.support.utils;

/**
 * @Date 2017/5/15
 * @Author wenzheng.liu
 * @Description Clase de conversión digital
 * @ClassPath com.fitpolo.support.utils.DigitalConver
 */
public class DigitalConver {

    /**
     * @Date 2017/5/10
     * @Author wenzheng.liu
     * @Description byte a hexadecimal
     */
    public static String byte2HexString(byte b) {
        return String.format("%02X", b);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * @Date 2017/5/15
     * @Author wenzheng.liu
     * @Description Hexadecimal a decimal
     */
    public static String decodeToString(String data) {
        String string = Integer.toString(Integer.parseInt(data, 16));
        return string;
    }

    /**
     * @Date 2017/5/16
     * @Author wenzheng.liu
     * @Description Hex a binario
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * @Date 2017/5/16
     * @Author wenzheng.liu
     * @Description byte a binario
     */
    public static String byte2binaryString(byte b) {
        return hexString2binaryString(byte2HexString(b));
    }

    /**
     * @Date 2017/6/9
     * @Author wenzheng.liu
     * @Description 2 hex a hex
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * @Date 2017/8/15
     * @Author wenzheng.liu
     * @Description Convierta la matriz de bytes bRefArr en un entero. Los bits de orden inferior de la matriz de bytes son los bits de orden inferior del entero.
     */
    public static int byteArr2Int(byte[] bRefArr) {
        return Integer.parseInt(DigitalConver.bytesToHexString(bRefArr), 16);
    }

    public static String byteArr2Str(byte[] bRefArr) {
        return Integer.toString(byteArr2Int(bRefArr));
    }

    public static int byte2Int(byte b) {
        return b & 0xFF;
    }

    /**
     * @Date 2017/8/14 0014
     * @Author wenzheng.liu
     * @Description Convertir entero a matriz de bytes
     */
    public static byte[] int2ByteArr(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
            bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
        }
        //Los datos se invierten y deben invertirse
        byte[] bytes = new byte[iArrayLen];
        for (int i = 0; i < bLocalArr.length; i++) {
            bytes[bLocalArr.length - 1 - i] = bLocalArr[i];
        }
        return bytes;
    }

    // String to Hex
    public static String string2Hex(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    // Hexagonal a cuerda
    public static String hex2String(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static byte[] hex2bytes(String hex) {
        if (hex.length() % 2 == 1) {
            hex = "0" + hex;
        }
        byte[] data = new byte[hex.length() / 2];
        for (int i = 0; i < data.length; i++) {
            try {
                data[i] = (byte) (0xff & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
