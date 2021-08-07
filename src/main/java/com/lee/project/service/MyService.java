package com.lee.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lee.project.dao.MyMapper;

/**
 * @author Li Menglu
 * @date 2021/7/12 15:18
 */
public interface MyService<T> extends IService<T> {

    default boolean removeByIdWithFill(T entity){
        int count = ((MyMapper<T>) getBaseMapper()).deleteByIdWithFill(entity);
        return SqlHelper.retBool(count);
    }
}
