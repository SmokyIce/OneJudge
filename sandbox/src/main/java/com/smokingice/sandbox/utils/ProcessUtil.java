package com.smokingice.sandbox.utils;

import com.smokingice.sandbox.model.ExecuteMessage;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author smokingice
 */
public class ProcessUtil {
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String opName){
        ExecuteMessage executeMessage = new ExecuteMessage();


        try {
            //等待编译完成
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            if (exitValue == 0){
                //获取控制台信息，正常输出流
                System.out.println(opName + "成功");
                BufferedReader br = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                //逐行获取
                String compileOutputLine;
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                while((compileOutputLine = br.readLine()) != null){
                    compileOutputStringBuilder.append(compileOutputLine).append("\n");
                }
                executeMessage.setOutput(compileOutputStringBuilder.toString());

            }else {
                System.out.println(opName + "失败，错误码为：" + exitValue);
                //获取控制台信息，正常输出流
                BufferedReader br = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                //逐行获取
                String compileOutputLine;
                StringBuilder compileErrorOutputStringBuilder = new StringBuilder();
                while((compileOutputLine = br.readLine()) != null){
                    compileErrorOutputStringBuilder.append(compileOutputLine).append("\n");
                }
                //获取控制台信息，错误输出流
                BufferedReader errbr = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                //逐行获取
                String errorCompileOutputLine;
                while((errorCompileOutputLine = errbr.readLine()) != null){
                    compileErrorOutputStringBuilder.append(errorCompileOutputLine).append("\n");
                }
                executeMessage.setError(compileErrorOutputStringBuilder.toString());
            }
            stopWatch.stop();
            long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
            executeMessage.setTime(lastTaskTimeMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }
}
