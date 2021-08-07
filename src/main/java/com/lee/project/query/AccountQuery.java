package com.lee.project.query;

import lombok.Data;

/**
 * @author Li Menglu
 * @date 2021/7/9 16:29
 */
@Data
public class AccountQuery {
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 时间范围
     */
    private String createTimeRange;

    /**
     * 当前页
     */
    private Long page;

    /**
     * 每页的条数
     */
    private Long limit;

}
