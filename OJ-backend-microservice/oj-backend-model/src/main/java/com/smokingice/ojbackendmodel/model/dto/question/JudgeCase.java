package com.smokingice.ojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * @author SmokingIce
 * 题目用例
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;
     /**
     * 输出用例
     */
     private String output;
}
