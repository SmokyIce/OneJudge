package com.smokingice.ojbackendjudgeservice.controller.inner;

import com.smokingice.ojbackendjudgeservice.judge.JudgeService;
import com.smokingice.ojbackendmodel.model.entity.QuestionSubmit;
import com.smokingice.ojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 该服务仅内部调用
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;
    /**
     * 判题服务内部调用
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do/judge")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
