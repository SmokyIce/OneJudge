package com.smokingice.ojbackendjudgeservice.judge.codesandbox.impl;


import com.smokingice.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 三方代码沙箱
 * @author smokingice
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
