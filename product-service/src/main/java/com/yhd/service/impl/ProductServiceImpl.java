package com.yhd.service.impl;
import com.yhd.pojo.Product;
import com.yhd.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author 05887-zhangfei
 * @version Id: ProductServiceImpl.java, v 0.1 2020/11/12 14:31 zhangfei Exp $
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product selectProductById(Integer id) {
        return new Product(id,"冰箱",2,3500.0);
    }
}
