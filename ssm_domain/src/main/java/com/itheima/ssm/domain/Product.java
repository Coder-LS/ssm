package com.itheima.ssm.domain;

import com.itheima.ssm.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 产品信息
 */
public class Product {
    private String id; // 主键
     private String productNum; // 编号 唯一
     private String productName; // 名称
     private String cityName; // 出发城市

    /**
     * org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 1 errors
     * Field error in object 'product' on field 'departureTime': rejected value [2021-05-05 10:30]; codes [typeMismatch.product.departureTime,
     * typeMismatch.departureTime,typeMismatch.java.util.Date,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.departureTime,departureTime]; arguments []; default message [departureTime]];
     * default message [Failed to convert property value of type 'java.lang.String' to required type 'java.util.Date' for property 'departureTime'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [java.util.Date] for value '2021-05-05 10:30'; nested exception is java.lang.IllegalArgumentException]
     *
     * springmvc类型转换
     *@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")  局部转化方式
     *
     * 类型转换器Converter 全局类型转换 参考前期代码
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date departureTime; // 出发时间
     private String departureTimeStr;
     private double productPrice; // 产品价格
     private String productDesc; // 产品描述
     private Integer productStatus; // 状态 0 关闭 1 开启
     private String productStatusStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureTimeStr() {
        if(departureTime!=null){
            departureTimeStr= DateUtils.date2String(departureTime,"yyyy-MM-dd HH:mm:ss");
        }
        return departureTimeStr;
    }

    public void setDepartureTimeStr(String departureTimeStr) {
        this.departureTimeStr = departureTimeStr;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductStatusStr() {
        if (productStatus!=null){
            // 状态 0 关闭 1 开启
            if (productStatus==0) {
                productStatusStr="关闭";
            }
            if (productStatus==1) {
                productStatusStr="开启";
            }
        }
        return productStatusStr;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }
}
