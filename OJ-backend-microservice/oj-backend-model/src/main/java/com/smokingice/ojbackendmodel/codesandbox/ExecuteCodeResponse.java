package com.smokingice.ojbackendmodel.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author smokingice
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    /**
     * 输出信息
     */
    private List<String> outputList;
    /**
     * 执行状态
     */
    private Integer status;
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;
}
