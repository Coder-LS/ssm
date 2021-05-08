package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.IProductDao;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductDao productDao;

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public List<Product> findAll(int page,int size) throws Exception{
        //参数pageNum是页码值,参数pageSize是每页显示条数,必写作在真正调用分页代码之前
        PageHelper.startPage(page,size);
        return productDao.findAll();
    }

    @Override
    public void deleteProductById(String productId) throws Exception  {
        //从sysLog表中删除
         productDao.deleteProductById(productId);
    }

    //改变产品的状态 开启1-->关闭0  关闭0-->开启1
    @Override
    public void updateProductStatus(String productId, int productstatus) throws Exception  {
        if (productstatus==0){
            productDao.updateProductStatusOn(productId);
        }else {
            productDao.updateProductStatusOff(productId);
        }

    }

    @Override
    public Product findById(String productId) throws Exception {
        return productDao.findById(productId);
    }

    @Override
    public void updateProduct(Product product,String productId) throws Exception{
        productDao.updateProduct(product,productId);
    }
}
