package com.yhd.service;

import com.yhd.pojo.Product;

/**
 * @author 05887-zhangfei
 * @version Id: ProductService.java, v 0.1 2020/11/12 14:30 zhangfei Exp $
 */
public interface ProductService {

    /**
     * 根据主键查询商品
     *
     */
    Product selectProductById(Integer id);
}
