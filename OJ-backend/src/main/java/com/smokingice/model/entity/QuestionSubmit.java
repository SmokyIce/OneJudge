package com.smokingice.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.smokingice.model.enums.QuestionSubmitLanguageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帖子点赞
 * @TableName question_submit
 */
@TableName(value ="question_submit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmit {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题状态(0 - 待判题、1 - 判题中、2 - 成功、3 - 失败)
     */
    private Integer status;

    /**
     * 判题信息（json 对象）
     */
    private String judgeInfo;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}