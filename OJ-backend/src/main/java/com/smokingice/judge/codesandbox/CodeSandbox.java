package com.smokingice.judge.codesandbox;

import com.smokingice.judge.codesandbox.model.ExecuteCodeRequest;
import com.smokingice.judge.codesandbox.model.ExecuteCodeResponse;

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
