package com.lee.project.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import java.util.*;

/**
 * @author Li Menglu
 * @date 2021/7/8 19:12
 */


public class ResultUtil {
    /**
     * 构建分页查询返回结果
     * @param page
     * @return
     */
    public static R<Map<String,Object>> buildPageR(IPage<?> page){
        HashMap<String, Object> data = new HashMap<>();
        data.put("count",page.getTotal());
        data.put("records",page.getRecords());
        return R.ok(data);
    }

    /**
     * 成功或失败的响应信息
     * @param success
     * @return
     */
    public static R<Object> buildR(boolean success){
        if(success){
            return R.ok(null);
        }
        return R.failed("操作失败");
    }

}
