package com.lee.project.vo;

import lombok.Data;
import java.util.*;

/**
 * @author Li Menglu
 * @date 2021/7/8 15:06
 */
@Data
public class ResourceVO {

    // VO value object

    /**
     * 主键
     */
    private Long resourceId;


    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 子资源
     */
    private List<ResourceVO> subs;
}
