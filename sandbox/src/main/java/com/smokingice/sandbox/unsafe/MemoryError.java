package com.smokingice.sandbox.unsafe;

import java.util.ArrayList;
import java.util.List;

/**
 * 无线占用空间，浪费系统内存
 */
public class MemoryError {
    public static void main(String[] args) throws InterruptedException {
        List<byte[]> bytes = new ArrayList<>();
        while (true) {
            bytes.add(new byte[1024 * 1024]);
        }
    }
}
