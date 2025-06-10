package com.smokingice.ojbackendjudgeservice.judge.codesandbox;

import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author smokingice
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox{

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info ("代码沙箱请求参数：{}",executeCodeRequest);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info ("代码沙箱返回参数：{}",executeCodeResponse);
        return executeCodeResponse;
    }
}
