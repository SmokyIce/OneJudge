package com.smokingice.judge;

import com.smokingice.model.entity.QuestionSubmit;
/**
 * 判题服务
 * @author smokingice
 * @description:
 * @date 2023/12/27 10:05
 */
public interface JudgeService {
    /**
     * 判题服务
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
