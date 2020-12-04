package com.yhd.pojo;

import java.io.Serializable;

/**
 * @author 05887-zhangfei
 * @version Id: Product.java, v 0.1 2020/11/12 14:28 zhangfei Exp $
 */
public class Product implements Serializable {
    private Integer id;
    private String productName;
    private Integer productNum;
    private Double productPrice;

    public Product(Integer id, String productName, Integer productNum, Double productPrice) {
        this.id = id;
        this.productName = productName;
        this.productNum = productNum;
        this.productPrice = productPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
