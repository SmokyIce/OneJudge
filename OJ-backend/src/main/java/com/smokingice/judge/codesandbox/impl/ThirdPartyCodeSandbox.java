package com.smokingice.judge.codesandbox.impl;

import com.smokingice.judge.codesandbox.CodeSandbox;
import com.smokingice.judge.codesandbox.model.ExecuteCodeRequest;
import com.smokingice.judge.codesandbox.model.ExecuteCodeResponse;

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
