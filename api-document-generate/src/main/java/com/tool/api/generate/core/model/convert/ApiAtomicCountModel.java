package com.tool.api.generate.core.model.convert;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接口参数，计数model
 *
 * @author mengqiang
 */
@Data
public class ApiAtomicCountModel {

    /**
     * 参数总数
     */
    private AtomicInteger paramTotalSize;
    /**
     * 参数总层级
     */
    private AtomicInteger paramTotalLevel;

    private AtomicInteger hasChildParamTotalSize;

    /**
     * 当前层级
     */
    private AtomicInteger currentLevel;

    public ApiAtomicCountModel(AtomicInteger paramTotalSize, AtomicInteger paramTotalLevel, AtomicInteger currentLevel) {
        this.paramTotalSize = paramTotalSize;
        this.paramTotalLevel = paramTotalLevel;
        this.currentLevel = currentLevel;
    }
}