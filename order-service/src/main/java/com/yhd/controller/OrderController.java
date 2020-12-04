package com.yhd.controller;

import com.yhd.pojo.Order;
import com.yhd.service.OrderSerVice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 05887-zhangfei
 * @version Id: OrderController.java, v 0.1 2020/11/12 15:23 zhangfei Exp $
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderSerVice orderSerVice;

    /**
     * 根据id查询订单
     *
     * @return
     */
    @GetMapping("/{id}")
    public Order selectOrderById(@PathVariable("id") Integer id) {
        return orderSerVice.selectOrderById(id);
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
