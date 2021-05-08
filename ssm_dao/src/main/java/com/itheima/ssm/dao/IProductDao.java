package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @author W12777
 */
public interface IProductDao {

    //根据id查询产品
    @Select("select * from product where id=#{id}")
    public Product findById(String id) throws Exception;

    //查询所有的产品信息
    @Select("select * from product")
    public List<Product> findAll() throws Exception;

    //注意事务操作
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product);


    @Delete("delete from product where id=#{productId}")
    void deleteProductById(@Param("productId") String productId) throws Exception;


    //开启产品状态为1
    @Update("update product set productStatus = '1' where id=#{productId}")
    void updateProductStatusOn(String productId);

    //关闭产品状态为0
    @Update("update product set productStatus = '0' where id=#{productId}")
    void updateProductStatusOff(String productId);

    @Update("update product set productNum=#{productNum},productName=#{productName},cityName=#{cityName},departureTime=#{departureTime},productPrice=#{productPrice},productDesc=#{productDesc} where id=#{productId}")
    void updateProduct(Product product,String productId) ;
}
