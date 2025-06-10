package com.smokingice.sandbox.unsafe;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 读取服务器文件，泄露文件隐私
 */
public class ReadFileError {
    public static void main(String[] args) throws InterruptedException, IOException {
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/application.yml";
        List<String> list = Files.readAllLines(Paths.get(filePath));
        System.out.println(String.join("\n", list));
    }
}
