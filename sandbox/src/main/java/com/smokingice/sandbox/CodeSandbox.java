package com.smokingice.sandbox;


import com.smokingice.sandbox.model.ExecuteCodeRequest;
import com.smokingice.sandbox.model.ExecuteCodeResponse;

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
