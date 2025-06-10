package com.smokingice.ojbackendserviceclient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smokingice.ojbackendmodel.model.entity.Question;
import com.smokingice.ojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
* @author smokingice
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2025-05-31 09:58:17
*/
@FeignClient(name="oj-backend-question-service", path="/api/question/inner")
public interface QuestionFeignClient {

    /**
     * 根据 id 获取 question
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

}
