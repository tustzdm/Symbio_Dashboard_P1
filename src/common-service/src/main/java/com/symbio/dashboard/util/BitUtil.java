package com.symbio.dashboard.util;

public class BitUtil {

    //将data字节型数据转换为0~255 (0xFF 即BYTE)。
    public static int getUnsignedByte(byte data) {
        return data & 0x0FF;
    }

    //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
    public int getUnsignedByte(short data) {
        return data & 0x0FFFF;
    }

    //将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。
    public long getUnsignedIntt(int data) {
        return data & 0x0FFFFFFFFl;
    }

    public static boolean isValid(int data, int pos) {
        return ((data >> pos) & 0x00000001) > 0;
    }
}
