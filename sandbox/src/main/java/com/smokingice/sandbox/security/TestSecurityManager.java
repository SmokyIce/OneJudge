package com.smokingice.sandbox.security;


import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestSecurityManager {
    public static void main(String[] args) {
        System.setSecurityManager(new MySecurityManager());
        //读文件
        List<String> list = FileUtil.readLines(new File("D:\\test.txt"), StandardCharsets.UTF_8);
        //
        System.out.println(list);
    }
}
