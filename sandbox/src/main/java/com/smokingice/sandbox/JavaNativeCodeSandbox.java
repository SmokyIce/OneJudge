package com.smokingice.sandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.WordTree;
import com.smokingice.sandbox.model.ExecuteCodeRequest;
import com.smokingice.sandbox.model.ExecuteCodeResponse;
import com.smokingice.sandbox.model.ExecuteMessage;
import com.smokingice.sandbox.model.JudgeInfo;
import com.smokingice.sandbox.security.DefaultSecurityManager;
import com.smokingice.sandbox.security.MySecurityManager;
import com.smokingice.sandbox.utils.ProcessUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class JavaNativeCodeSandbox implements CodeSandbox{
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";
    private static final String GLOBAL_CODE_FILE_NAME = "Main.java";
    private static final long TIME_OUT = 5000L;
    private static final String SECURITY_MANAGER_PATH = "E:\\project\\OJ在线判题系统源码\\sandbox\\src\\main\\resources\\security\\MySecurityManager.class";
    private static final String SECURITY_MANAGER_CLASS_NAME = "MySecurityManager";
    private static final List<String> BLACK_LIST = Arrays.asList(
            "java.lang.SecurityManager",
            "exit()",
            "exit",
            "getSecurityManager()",
            "setSecurityManager()",
            "exec()",
            "ProcessBuilder",
            "File");

    private static final WordTree WORD_TREE;
    static{
        //单词树！
        WORD_TREE = new WordTree();
        WORD_TREE.addWords(BLACK_LIST);
    }

    public static void main(String[] args) {
        JavaNativeCodeSandbox javaNativeCodeSandbox = new JavaNativeCodeSandbox();
        String string = ResourceUtil.readStr("testCode/unsafe/RunFileError.java", StandardCharsets.UTF_8);
        ExecuteCodeRequest  executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(Arrays.asList("1 2", "2 3"))
                .code(string)
                .language("java")
                .build();
        System.out.println(javaNativeCodeSandbox.executeCode(executeCodeRequest));
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String userDir = System.getProperty("user.dir");

        //安全性检查
//        System.setSecurityManager(new MySecurityManager());
        //校验代码安全性
        FoundWord foundWord = WORD_TREE.matchWord(code);
        if(foundWord != null){
            System.out.println("包含敏感词：" + foundWord.getFoundWord());
            return ExecuteCodeResponse.builder()
                    .status(2)
                    .message("代码中有非法字符")
                    .build();
        }

        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        //第一次启动的目录不不存在，新建
        if(FileUtil.exist(globalCodePathName)){
            FileUtil.mkdir(globalCodePathName);
        }
        //把用户的代码隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_CODE_FILE_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, "utf-8");

        //2. 编译代码，得到class文件
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            //等待编译完成
            ExecuteMessage executeMessage = ProcessUtil.runProcessAndGetMessage(compileProcess, "编译");
            System.out.println(executeMessage);
        } catch (IOException e) {
            return getErrorResponse(e);
        }

        //3. 执行代码，得到输出结果

        List<ExecuteMessage> messageList = new ArrayList<>();
        for(String inputArgs : inputList){
//            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                //超时处理
                new Thread(()->{
                    try {
                        Thread.sleep(TIME_OUT);
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteMessage executeMessage = ProcessUtil.runProcessAndGetMessage(runProcess, "运行");
                System.out.println(executeMessage);
                messageList.add(executeMessage);
            } catch (IOException e) {
                return getErrorResponse(e);
            }
        }
        //4. 手机整理输出结果
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        //取最大值
        long maxTime = 0;
        for (ExecuteMessage executeMessage : messageList) {
            String errorMsg = executeMessage.getError();
            if (StrUtil.isNotBlank((errorMsg))){
                executeCodeResponse.setMessage(errorMsg);
                executeCodeResponse.setStatus(3);
                break;
            }
            Long time = executeMessage.getTime();
            if (time != null){
                maxTime = Math.max(maxTime, time);
            }
            outputList.add(executeMessage.getOutput());
        }
        if (outputList.size() == messageList.size()){
            executeCodeResponse.setStatus(0);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        //暂不处理内存，非常麻烦
        //judgeInfo.setMemory();
        executeCodeResponse.setJudgeInfo(judgeInfo);

        //5. 文件清理、防止服务器空间不足
        if(userCodeFile.getParentFile() != null) {
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("文件删除" + (del ? "成功": "失败"));
        }

        //6. 错误处理，提供程序健壮性
        return executeCodeResponse;
    }

    private ExecuteCodeResponse getErrorResponse(Throwable e){
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        //表示服务器出错，如编译报错
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setMessage(e.getMessage());
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }
}
