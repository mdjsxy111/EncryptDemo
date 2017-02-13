package com.rock.teach.utils.encrypt;

import java.security.MessageDigest;

public class MD5 {

    /**
     * MD5加密
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    public static String encryptMD5ToString(byte[] data) throws Exception {
        byte[] bytes = encryptMD5(data);
        StringBuffer sb = new StringBuffer();
        for (byte b: bytes){
            sb.append(String.format("%2x",b));
        }
        return sb.toString();
    }

}
