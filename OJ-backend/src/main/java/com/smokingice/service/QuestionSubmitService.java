package com.smokingice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smokingice.model.dto.question.QuestionQueryRequest;
import com.smokingice.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.smokingice.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.smokingice.model.entity.Question;
import com.smokingice.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smokingice.model.entity.User;
import com.smokingice.model.vo.QuestionSubmitVO;
import com.smokingice.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author smokingice
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-05-31 09:59:03
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param questionSubmit
     * @param user
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User user);

    /**
     * 分页获取帖子封装
     *
     * @param questionSubmitPage
     * @param user
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User user);
}
