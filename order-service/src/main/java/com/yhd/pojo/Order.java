package com.yhd.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 05887-zhangfei
 * @version Id: Order.java, v 0.1 2020/11/12 14:48 zhangfei Exp $
 */
public class Order implements Serializable {
    private Integer id;
    private String orderNo;
    private String orderAddress;
    private Double totalPrice;
    private List<Product> productList;

    public Order(Integer id, String orderNo, String orderAddress, Double totalPrice, List<Product> productList) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderAddress = orderAddress;
        this.totalPrice = totalPrice;
        this.productList = productList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
