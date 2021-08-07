package com.lee.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lee.project.entity.Resource;
import com.lee.project.dao.ResourceMapper;
import com.lee.project.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.project.vo.ResourceVO;
import com.lee.project.vo.TreeVO;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author Lee Menglu
 * @since 2021-07-07
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    /**
     * @param roleId
     * @return
     * 根据角色id查询角色具有的资源
     * */
    @Override
    public List<ResourceVO> listResourceByRoleId(long roleId) {
        // 查第一级目录
        QueryWrapper<Resource> query = Wrappers.<Resource>query();
        query.eq("rr.role_id",roleId).isNull("re.parent_id").orderByAsc("re.sort");
        List<ResourceVO> resourceVOS = baseMapper.listResource(query);

        // 查下级菜单
        resourceVOS.forEach(r ->{
            Long resourceId = r.getResourceId();
            QueryWrapper<Resource> subWrapper = Wrappers.<Resource>query();
            subWrapper.eq("rr.role_id",roleId)
                    .eq("re.parent_id",resourceId).orderByAsc("re.sort");
            List<ResourceVO> subResourceVOS = baseMapper.listResource(subWrapper);
            if(CollectionUtils.isNotEmpty(subResourceVOS)){
                r.setSubs(subResourceVOS);
            }
        });
        return resourceVOS;
    }

    /**
     * 查询系统资源 供前端组件渲染
     * @return
     */
    @Override
    public List<TreeVO> listResource(Long roleId,Integer flag) {
        // 如果被新增方法调用
        if(roleId==null){
            LambdaQueryWrapper<Resource> wrapper = Wrappers.<Resource>lambdaQuery()
                    .isNull(Resource::getParentId)
                    .orderByAsc(Resource::getSort);
            List<Resource> resources = list(wrapper);
            List<TreeVO> treeVOS=resources.stream().map(r->{
                TreeVO treeVO=new TreeVO();
                treeVO.setId(r.getResourceId());
                treeVO.setTitle(r.getResourceName());

                LambdaQueryWrapper<Resource> subWrapper = Wrappers.<Resource>lambdaQuery()
                        .eq(Resource::getParentId,r.getResourceId())
                        .orderByAsc(Resource::getSort);
                List<Resource> subResources = list(subWrapper);
                if(CollectionUtils.isNotEmpty(subResources)){
                    List<TreeVO> children=subResources.stream().map(sub->{
                        TreeVO subTreeVO=new TreeVO();
                        subTreeVO.setId(sub.getResourceId());
                        subTreeVO.setTitle(sub.getResourceName());
                        return subTreeVO;
                    }).collect(Collectors.toList());
                    treeVO.setChildren(children);
                }
                return treeVO;
            }).collect(Collectors.toList());
            return treeVOS;

        }else{
            QueryWrapper<Resource> query = Wrappers.<Resource>query();
            query.eq(flag==1,"rr.role_id",roleId)
                    .isNull("re.parent_id").orderByAsc("re.sort");
            List<TreeVO> treeVOS = baseMapper.listResourceByRoleId(query, roleId);
            treeVOS.forEach(t->{
                t.setChecked(false);
                Long id = t.getId();
                QueryWrapper<Resource> subWrapper = Wrappers.<Resource>query();
                subWrapper.eq(flag==1,"rr.role_id",roleId)
                          .eq("re.parent_id",id).orderByAsc("re.sort");

                List<TreeVO> children = baseMapper.listResourceByRoleId(subWrapper, roleId);
                if(CollectionUtils.isNotEmpty(children)){
                    t.setChildren(children);
                }
            });
            return treeVOS;

        }

    }

    /**
     * 将资源转换为代码模块名称的集合
     * @param resourceVOS
     * @return
     */

    @Override
    public HashSet<String> convert(List<ResourceVO> resourceVOS) {
        HashSet<String> module=new HashSet<>();
        resourceVOS.forEach(r->{
            String url=r.getUrl();
            if(StringUtils.isNotBlank(url)){
                module.add(url.substring(0,url.indexOf("/")));
            }

            List<ResourceVO> subResourceVOs=r.getSubs();
            if(CollectionUtils.isNotEmpty(subResourceVOs)){
                subResourceVOs.forEach(sub->{
                    String subUrl= sub.getUrl();
                    if(StringUtils.isNotBlank(subUrl)){
                        module.add(subUrl.substring(0,subUrl.indexOf("/")));
                    }
                });
            }
        });
        return module;
    }
}
