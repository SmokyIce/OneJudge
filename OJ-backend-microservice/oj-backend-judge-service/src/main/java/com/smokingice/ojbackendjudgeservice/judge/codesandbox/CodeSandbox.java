package com.smokingice.ojbackendjudgeservice.judge.codesandbox;


import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 * @author smokingice
 */
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest request
     * @return response
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

}
