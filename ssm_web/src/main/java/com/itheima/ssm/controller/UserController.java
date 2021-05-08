package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    //查询指定id的用户
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user",userInfo);
        mv.setViewName("user-show1");
        return mv;
    }

    //用户添加(存储到数据库中密码应该是加密的 noop UserServiceImpl)
    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) throws Exception {
        userService.save(userInfo);
        return "redirect:findAll.do";
    }

    /**
     * 查询所有用户
     * @return
     * @throws Exception

    @RolesAllowed("ADMIN")
    //@RolesAllowed({"USER", "ADMIN"}) 该方法只要具有"USER", "ADMIN"任意一种权限就可以访问。这里可以省 略前缀ROLE_，实际的权限可能是ROLE_ADMIN
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList = userService.findAll();
        mv.addObject("userList", userList);
        mv.setViewName("user-list");
        return mv;
    }*/

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
                    Integer size) throws Exception {//int size换成integer size
        //java.lang.NoSuchMethodException: com.itheima.ssm.controller.OrdersController.findAll(java.lang.Integer, java.lang.Integer)

        ModelAndView mv=new ModelAndView();
        List<UserInfo> usersList = userService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(usersList);
        //PageInfo就是一个分页的Bean
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("user-list");
        return mv;
    }

    //查询用户以及用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) String userid) throws Exception {
        ModelAndView mv = new ModelAndView();
        //1.根据用户id查询用户
        UserInfo userInfo = userService.findById(userid);
        //2.根据用户id查询可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(userid);
        mv.addObject("user", userInfo);
        mv.addObject("roleList", otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }


    //给用户添加角色  传过来的UserId 和选中的角色rolesId数组
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId", required = true) String userId, @RequestParam(name = "ids", required = true) String[] roleIds) throws Exception {
        userService.addRoleToUser(userId, roleIds);
        return "redirect:findAll.do";
    }


    //删除角色
    @RolesAllowed("ADMIN")
    @RequestMapping("/DeleteUser.do")
    public String deleteUser(@RequestParam(name="id",required = true) String userId) throws Exception {
        userService.deleteUserById(userId);
        return "redirect:findAll.do";
    }
}
