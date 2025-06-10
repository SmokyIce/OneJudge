package com.smokingice.ojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.smokingice.ojbackendcommon.common.ErrorCode;
import com.smokingice.ojbackendcommon.exception.BusinessException;
import com.smokingice.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeResponse;
import org.springframework.beans.factory.annotation.Value;

/**
 *  远程代码沙箱
 * @author smokingice
 */
public class RemoteCodeSandBox implements CodeSandbox {

    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "smokingiceKey";

    @Value("${codesandbox.url}")
    private String sandboxUrl;
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String post = HttpUtil.createPost(sandboxUrl)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StrUtil.isBlank(post)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "无法获取执行结果");
        }
        return JSONUtil.toBean(post, ExecuteCodeResponse.class);
    }
}
