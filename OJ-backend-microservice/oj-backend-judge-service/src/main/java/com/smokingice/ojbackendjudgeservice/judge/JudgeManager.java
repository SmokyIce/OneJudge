package com.smokingice.ojbackendjudgeservice.judge;

import com.smokingice.ojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.smokingice.ojbackendjudgeservice.judge.strategy.JavaJudgeStrategy;
import com.smokingice.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.smokingice.ojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.smokingice.ojbackendmodel.codesandbox.JudgeInfo;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化）
 *
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
