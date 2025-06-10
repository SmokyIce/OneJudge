package com.smokingice.ojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.smokingice.ojbackendcommon.common.ErrorCode;
import com.smokingice.ojbackendcommon.exception.BusinessException;
import com.smokingice.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.smokingice.ojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.smokingice.ojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.smokingice.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.smokingice.ojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.smokingice.ojbackendmodel.codesandbox.JudgeInfo;
import com.smokingice.ojbackendmodel.model.dto.question.JudgeCase;
import com.smokingice.ojbackendmodel.model.entity.Question;
import com.smokingice.ojbackendmodel.model.entity.QuestionSubmit;
import com.smokingice.ojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.smokingice.ojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${codesandbox.type:remote}")
    private String type;
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private JudgeManager judgeManager;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1. 获取到题目id，获取对应提交信息、包含代码、编程语言等
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        Question question = questionFeignClient.getQuestionById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2. 如果题目提交状态不为等待中，就不用重复执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        //3. 更改判题状态
        QuestionSubmit questionSubmitUpdate = QuestionSubmit.builder()
                .userId(questionSubmit.getUserId())
                .status(QuestionSubmitStatusEnum.RUNNING.getValue())
                .build();
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态错误");
        }

        //4. 调用沙箱，获取执行结果
        CodeSandbox codeSandbox = new CodeSandboxProxy(CodeSandboxFactory.newInstance(type));
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        List<JudgeCase> judgeCase = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList  = judgeCase.stream().map( JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        //5. 根据沙箱执行结果，设置题目判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCase(judgeCase);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //6. 修改数据库判题结果
        questionSubmitUpdate = QuestionSubmit.builder()
                .id(questionSubmit.getId())
                .status(QuestionSubmitStatusEnum.SUCCEED.getValue())
                .judgeInfo(JSONUtil.toJsonStr(judgeInfo))
                .build();
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if ( !update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目判题结果更新错误");
        }
        return questionFeignClient.getQuestionSubmitById(questionSubmitId);
    }
}
