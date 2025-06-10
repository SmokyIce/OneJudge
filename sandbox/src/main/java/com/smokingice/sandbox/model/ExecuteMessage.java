package com.smokingice.sandbox.model;

import lombok.Data;

/**
 * 进程执行信息
 */
@Data
public class ExecuteMessage {
    private Integer exitValue;
    private String output;
    private String error;
    private Long time;
}
