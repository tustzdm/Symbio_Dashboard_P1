package com.symbio.dashboard.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 *
 * @author Util
 * @version 1.0
 * @since
 */
public class MD5Util {

    /**
     * 将字符串转换成md5
     *
     * @param source
     * @return
     */
    public static String encrypt(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte b[] = md.digest();
            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        // 1: c4ca4238a0b923820dcc509a6f75849b
        // symbio123: 6dde3035474f3aaad59c25687a41cd07
        String strText = "whq44533074";
        System.out.println(String.format("Content: %s, MD5: %s", strText, MD5Util.encrypt(strText)));
    }
}
