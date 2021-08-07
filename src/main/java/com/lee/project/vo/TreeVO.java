package com.lee.project.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Li Menglu
 * @date 2021/7/10 13:24
 */
@Data
public class TreeVO {
    /**
     * 标题
     */
    private String title;

    private Long id;

    private List<TreeVO> children;

    /**
     * 标记是否选中
     */
    private boolean checked;
}
