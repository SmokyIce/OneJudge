package com.smokingice.judge.codesandbox.impl;

import com.smokingice.judge.codesandbox.CodeSandbox;
import com.smokingice.judge.codesandbox.model.ExecuteCodeRequest;
import com.smokingice.judge.codesandbox.model.ExecuteCodeResponse;
import com.smokingice.judge.codesandbox.model.JudgeInfo;
import com.smokingice.model.enums.JudgeInfoMessageEnum;
import com.smokingice.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 *  示例代码沙箱
 * @author smokingice
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        executeCodeResponse.setMessage("ok");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
