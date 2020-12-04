package com.yhd.service.impl;

import com.yhd.pojo.Order;
import com.yhd.service.OrderSerVice;
import com.yhd.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author 05887-zhangfei
 * @version Id: OrderSerViceImpl.java, v 0.1 2020/11/12 14:52 zhangfei Exp $
 */
@Service
public class OrderSerViceImpl implements OrderSerVice {
    @Autowired
    private ProductService productService;

    /**
     * 根据id查询订单的商品信息
     *
     * @param id
     * @return
     */
    @Override
    public Order selectOrderById(Integer id) {
        return new Order(id, "order-001", "中国", 2666D, Arrays.asList(productService.selectProductById(id)));
    }
}
