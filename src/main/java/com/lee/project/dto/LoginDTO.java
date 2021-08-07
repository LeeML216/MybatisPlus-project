package com.lee.project.dto;

import com.lee.project.entity.Account;
import lombok.Data;

/**
 * @author Li Menglu
 * @date 2021/7/7 19:23
 */
@Data
public class LoginDTO {

    /**
     * 重定向或跳转的路径
     */
    private String path;

    /**
     * 错误提示信息
     */
    private String error;

    /**
     * 登陆人信息
     */
    private Account account;
}
