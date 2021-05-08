package com.itheima.ssm.service;

import com.itheima.ssm.domain.Product;

import java.util.List;

/**
 * @author W12777
 */
public interface IProductService {

    public List<Product> findAll(int page,int size) throws Exception;

    void save(Product product) throws Exception;

    void deleteProductById(String productId) throws Exception;

    void updateProductStatus(String productId,int productstatus) throws Exception;

    Product findById(String productId) throws Exception;

    void updateProduct(Product product,String productId) throws Exception;
}
