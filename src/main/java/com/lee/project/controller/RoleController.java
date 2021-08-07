package com.lee.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.project.entity.Account;
import com.lee.project.entity.Role;
import com.lee.project.service.AccountService;
import com.lee.project.service.ResourceService;
import com.lee.project.service.RoleService;
import com.lee.project.util.ResultUtil;
import com.lee.project.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Lee Menglu
 * @since 2021-07-07
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private AccountService accountService;

    /**
     * 进入角色列表页面
     * @return
     */
    @GetMapping("toList")
    public String toList(){
        return "role/roleList";
    }
    /**
     * 查询方法
     * @param roleName
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public R<Map<String,Object>> list(String roleName,Long page,Long limit){
        LambdaQueryWrapper<Role> queryWrapper=Wrappers.<Role>lambdaQuery()
                .like(StringUtils.isNotBlank(roleName),Role::getRoleName,roleName)
                .orderByDesc(Role::getRoleId);
//       QueryWrapper<Role> queryWrapper = Wrappers.<Role>query()
//                .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
//                .orderByDesc("role_id");
        Page<Role> resultPage = roleService.page(new Page<>(page, limit), queryWrapper);

        return ResultUtil.buildPageR(resultPage);
    }

    /**
     * 进入新增页
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd(){
        return "role/roleAdd";
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Role role){
        System.out.println("角色添加失败！！");
        return ResultUtil.buildR(roleService.saveRole(role));
    }

    /**
     * 进入修改页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return "role/roleUpdate";
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Role role){
        System.out.println("角色修改失败");
        return ResultUtil.buildR(roleService.updateRole(role));
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id){
        // 无账号持有该角色的时候才可以删除
        Integer count = accountService.lambdaQuery().eq(Account::getRoleId, id).count();
        if(count>0){
            return R.failed("有账号正拥有该角色，不可删除！");
        }
        Role role=new Role();
        role.setRoleId(id);
        return ResultUtil.buildR(roleService.removeByIdWithFill(role));
    }

    /**
     *
     * @return
     */
    @ResponseBody
    @GetMapping({"listResource","listResource/{roleId}","listResource/{roleId}/{flag}"})
    public R<List<TreeVO>> listResource(@PathVariable(required = false) Long roleId
    ,@PathVariable(required = false) Integer flag){
        // flag 修改传0 详情传1
        return R.ok(resourceService.listResource(roleId,flag));
    }

    /**
     * 进入详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return "role/roleDetail";
    }
}
