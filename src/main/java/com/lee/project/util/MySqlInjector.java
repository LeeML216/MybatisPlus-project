package com.lee.project.util;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;

import java.util.List;

/**
 * @author Li Menglu
 * @date 2021/7/12 15:09
 */
public class MySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList=super.getMethodList(mapperClass);
        // 添加根据id逻辑删除数据 并带有字段填充功能
        methodList.add(new LogicDeleteByIdWithFill());
        return methodList;
    }

}
