package com.lee.project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.project.dto.LoginDTO;
import com.lee.project.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账号表 服务类
 * </p>
 *
 * @author Lee Menglu
 * @since 2021-07-07
 */
public interface AccountService extends MyService<Account> {

    LoginDTO login(String username, String password);

    /**
     * 分页查询账号
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Account> accountPage(Page<Account> page, Wrapper<Account> wrapper);

    /**
     * 根据accountId查询账号信息
     * @param id
     * @return
     */
    Account getAccountById(Long id);

}
