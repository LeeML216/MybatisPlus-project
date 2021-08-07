package com.lee.project.service;

import com.lee.project.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.project.entity.Role;
import com.lee.project.vo.ResourceVO;
import com.lee.project.vo.TreeVO;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author Lee Menglu
 * @since 2021-07-07
 */
public interface ResourceService extends MyService<Resource> {

    /**
     * @param roleId
     * @return
     * 根据角色id查询角色具有的资源
     * */
    List<ResourceVO> listResourceByRoleId(long roleId);

    /**
     * 查询系统资源 供前端组件渲染
     * @return
     */

    List<TreeVO> listResource(Long roleId,Integer flag);

    /**
     * 将资源转换为代码模块名称的集合
     * @param resourceVOS
     * @return
     */
    HashSet<String> convert(List<ResourceVO> resourceVOS);
}
