package com.symbio.dashboard.util;

public class SpecialCharacterUtils {

    // &lt; <
    // &gt; >
    // &amp; &
    // &quot; "
    // &reg; ®
    // &copy; ©
    // &trade; ™
    // &ensp;
    // &emsp;
    // &nbsp;
    private static String[] htmlSpecialCharacter =
            new String[]{
                    "&lt;", "&gt;", "&amp;", "&reg;", "&copy;", "&trade;", "&ensp;", "&emsp;", "&nbsp;"
            };
    private static String[] commonSpecialCharacter =
            new String[]{
                    "<", ">", "&", "\"", "'", " ", "!", "@", "#", "\\$", "%", "\\*", "\\^", "\\(", "\\)", "_",
                    "\\+", "\\=", "\\-", ",", "\\.", "，", "。", "\\?", "`", "~"
            };

    /**
     * html
     *
     * @param specialCharacterStr
     * @return
     */
    public static String htmlSpecialCharacterRemover(String specialCharacterStr) {
        int length = htmlSpecialCharacter.length;
        for (int i = 0; i < length; i++) {
            specialCharacterStr = specialCharacterStr.replace(htmlSpecialCharacter[i], "");
        }
        return specialCharacterStr;
    }

    /**
     * common special character
     *
     * @param specialCharacterStr
     * @return
     */
    public static String commonsSpecialCharacterRemover(String specialCharacterStr) {
        int length = commonSpecialCharacter.length;
        for (int i = 0; i < length; i++) {
            specialCharacterStr = specialCharacterStr.replace(commonSpecialCharacter[i], "");
        }
        return specialCharacterStr;
    }

    /**
     * all special character
     *
     * @param specialCharacterStr
     * @return
     */
    public static String specialCharacterRemover(String specialCharacterStr) {
        specialCharacterStr = htmlSpecialCharacterRemover(specialCharacterStr);
        specialCharacterStr = commonsSpecialCharacterRemover(specialCharacterStr);
        return specialCharacterStr;
    }

    public static void main(String[] args) {
        System.out.println(specialCharacterRemover("Screen Shot 2016-06-12 at 3.01.47 PM.png"));
    }
}
