package com.logan.ndp.handler.deduplication.service;


import com.logan.ndp.handler.deduplication.DeduplicationParam;


public interface DeduplicationService {

    /**
     * 去重
     *
     * @param param
     */
    void deduplication(DeduplicationParam param);
}
