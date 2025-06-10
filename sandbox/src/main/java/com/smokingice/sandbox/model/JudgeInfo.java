package com.smokingice.sandbox.model;

import lombok.Data;

/**
 * @author SmokingIce
 * 判题 信息
 */
@Data
public class JudgeInfo {

    /**
     * 时间限制 ms
     */
    private String message;
    /**
     * 消耗内存 KB
     */
    private Long memory;
    /**
     * 消耗时间 ms
     */
    private Long time;
}
