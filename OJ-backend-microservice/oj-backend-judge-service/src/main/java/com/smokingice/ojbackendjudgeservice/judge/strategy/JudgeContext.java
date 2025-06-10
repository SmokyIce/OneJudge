package com.smokingice.ojbackendjudgeservice.judge.strategy;

import com.smokingice.ojbackendmodel.codesandbox.JudgeInfo;
import com.smokingice.ojbackendmodel.model.dto.question.JudgeCase;
import com.smokingice.ojbackendmodel.model.entity.Question;
import com.smokingice.ojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 用于定义在策略中传递的参数
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private Question question;
    private List<JudgeCase> judgeCase;
    private QuestionSubmit questionSubmit;
}
