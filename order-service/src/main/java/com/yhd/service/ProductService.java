package com.yhd.service;

import com.yhd.fallback.ProductServiceFallbackFactory;
import com.yhd.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 05887-zhangfei
 * @version Id: ProductService.java, v 0.1 2020/11/12 14:51 zhangfei Exp $
 */
//声明需要调用的服务
@FeignClient(value = "product-service", fallbackFactory = ProductServiceFallbackFactory.class)
public interface ProductService {
    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    @GetMapping("/product/{id}")
    Product selectProductById(@PathVariable("id") Integer id);
}
