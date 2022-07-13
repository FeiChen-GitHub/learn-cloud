package com.learn.cloud.sentinelservice.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.learn.cloud.nacosuserservice.vo.CommonResult;

public class CustomBlockHandler {

    /**
     * must be static
     * @param e
     * @return
     */
    public static CommonResult handleException(BlockException e){
        return new CommonResult("自定义限流信息",500);
    }

}
