package com.smokingice.ojbackendserviceclient.service;


import com.smokingice.ojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 判题服务
 * @author smokingice
 * @description:
 * @date 2023/12/27 10:05
 */
@FeignClient(name="oj-backend-judge-service", path="/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 判题服务
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(long questionSubmitId);
}
