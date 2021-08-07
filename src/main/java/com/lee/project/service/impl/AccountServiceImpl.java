package com.lee.project.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.project.dto.LoginDTO;
import com.lee.project.entity.Account;
import com.lee.project.dao.AccountMapper;
import com.lee.project.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 账号表 服务实现类
 * </p>
 *
 * @author Lee Menglu
 * @since 2021-07-07
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public LoginDTO login(String username, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPath("redirect:/");

        Account account = lambdaQuery().eq(Account::getUsername, username).one();
        if(account == null) {
            loginDTO.setError("用户不存在");
            return  loginDTO;
        }

        MD5 md5 = new MD5(account.getSalt().getBytes());
        String digestHex = md5.digestHex(password);
        if(!digestHex.equals(account.getPassword())){
            loginDTO.setError("密码错误！");
            return  loginDTO;
        }

        loginDTO.setAccount(account);
        loginDTO.setPath("/login/main");
        return loginDTO;
    }


    /**
     * 分页查询账号
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public IPage<Account> accountPage(Page<Account> page, Wrapper<Account> wrapper) {
        return baseMapper.accountPage(page, wrapper);
    }

    /**
     * 根据accountId查询账号信息
     * @param id
     * @return
     */
    @Override
    public Account getAccountById(Long id) {
        return baseMapper.selectAccountById(id);
    }
}
