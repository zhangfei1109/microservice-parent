package com.yhd.service;

import com.yhd.pojo.Order;

/**
 * @author 05887-zhangfei
 * @version Id: OrderSerVice.java, v 0.1 2020/11/12 14:45 zhangfei Exp $
 */
public interface OrderSerVice {

    /**
     * 根据id查询订单
     *
     */
    Order selectOrderById(Integer id);
}
