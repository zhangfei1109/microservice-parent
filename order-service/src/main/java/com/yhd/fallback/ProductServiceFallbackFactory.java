package com.yhd.fallback;

import com.yhd.pojo.Product;
import com.yhd.service.ProductService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author 05887-zhangfei
 * @version Id: ProductServiceFallbackFactory.java, v 0.1 2020/11/12 15:20 zhangfei Exp $
 */
@Component
public class ProductServiceFallbackFactory implements FallbackFactory<ProductService> {

    @Override
    /**
     * 熔断机制
     */
    public ProductService create(Throwable throwable) {
        return  new ProductService() {
            @Override
            public Product selectProductById(Integer id) {
                return new Product(id,"拖地数据",2,6666D);
            }
        };

    }
}
