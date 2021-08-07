package com.lee.project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Li Menglu
 * @date 2021/7/12 15:14
 */
public interface MyMapper<T> extends BaseMapper<T> {
    /**
     * 逻辑删除带自动填充功能
     * @param entity
     * @return
     */
    int deleteByIdWithFill(T entity);
}
