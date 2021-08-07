package com.lee.project.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lee.project.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.project.vo.ResourceVO;
import com.lee.project.vo.TreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author Lee Menglu
 * @since 2021-07-07
 */
public interface ResourceMapper extends MyMapper<Resource> {

    /**
     * @param wrapper
     * 查询当前登陆人的资源
     * */
    List<ResourceVO> listResource(@Param(Constants.WRAPPER) Wrapper<Resource> wrapper);

    List<TreeVO> listResourceByRoleId(@Param(Constants.WRAPPER) Wrapper<Resource> wrapper
            ,@Param("roleId") Long roleId);

}
