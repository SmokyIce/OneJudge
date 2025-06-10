import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 运行程序
 */

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/木马程序.bat";
        Process exec = Runtime.getRuntime().exec(filePath);
        exec.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        //逐行获取
        String compileOutputLine;
        while ((compileOutputLine = br.readLine()) != null) {
            System.out.println(compileOutputLine);
        }
        System.out.println("执行成功");
    }
}