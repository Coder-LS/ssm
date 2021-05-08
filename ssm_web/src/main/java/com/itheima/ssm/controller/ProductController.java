package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;//applicationContext,xml选择MVC application Context

    //产品添加
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        productService.save(product);
        return "redirect:findAll.do";
    }

    //查询全部产品
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(
            @RequestParam(
                    name = "page",
                    required = true,
                    defaultValue="1") Integer page,
            @RequestParam(
                    name="size",
                    required = true,
                    defaultValue = "5")
                    Integer size
    ) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> productList = productService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(productList);
        //PageInfo就是一个分页的Bean
        mv.addObject("pageInfo",pageInfo);
//        mv.addObject("productList", ps); //items
        mv.setViewName("product-list1"); //页面显示jsp
        return mv;

    }


    //按照挑选的产品进行删除操作
    @RequestMapping("/deleteProduct.do")
    public String deleteProduct(@RequestParam(name="id",required = true) String productId) throws Exception {
        productService.deleteProductById(productId);
        return "redirect:findAll.do";
    }

    //改变产品的状态 开启-->关闭  关闭-->开启
    @RequestMapping("/updateProductStatus.do")
    public String updateProductStatus(@RequestParam(name="id",required = true) String productId,@RequestParam(name="status",required = true) Integer productstatus) throws Exception {
        productService.updateProductStatus(productId,productstatus);
        return "redirect:findAll.do";
    }

    //产品修改  先查出来到product-show  再修改
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String productId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Product product = productService.findById(productId);
        mv.addObject("product",product);
        mv.setViewName("product-show");
        return mv;
    }


    //改变产品的资料
    @RequestMapping("/update.do")
    public String updateProduct(Product product,@RequestParam(name="id",required = true) String productId) throws Exception {
        productService.deleteProductById(productId);
        productService.save(product);
        return "redirect:findAll.do";
    }

}
