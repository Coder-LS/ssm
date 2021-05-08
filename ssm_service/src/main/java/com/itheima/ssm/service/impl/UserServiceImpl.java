package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.IUserDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 调用dao完成认证操作,根据username/password
 * 必须让我们自己的userervice来实现UserDetailsService接口
 * UserDetails loadUserByUsername
 *
 */

@Service("userService") //user-service-ref="userService"
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    //也可以调用Utils里面的加密工具类加密
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserInfo findById(String id) throws Exception{

        return  userDao.findById(id);
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //对密码进行加密处理 存储到数据库中密码应该是加密的 noop UserServiceImpl
        //BCryptPasswordEncoder   spring-security.xml里面配置了加密Bean
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public List<UserInfo> findAll(int page,int size) throws Exception {
        //参数pageNum是页码值,参数pageSize是每页显示条数,必写作在真正调用分页代码之前
        PageHelper.startPage(page,size);
        return userDao.findAll();
    }

//    (UserDetails Ctrl+H)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //处理自己的用户对象封装成UserDetails
        //{noop}密码要加前缀不然500错误 密文
//         User user=new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));

        //userInfo.getStatus() == 0 ? false : true  //0是不可用的User is disabled  1是可登录的 (User 构造函数)boolean enabled
//        User user = new User(userInfo.getUsername(), "{noop}" + userInfo.getPassword(), userInfo.getStatus() == 0 ? false : true, true, true, true, getAuthority(userInfo.getRoles()));
        User user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 0 ? false : true, true, true, true, getAuthority(userInfo.getRoles()));

        return user;
    }

    //作用就是返回一个List集合，集合中装入的是角色描述
//    GrantedAuthority 里面的SimpleGrantedAuthority (Ctrl+H)
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return list;
    }


    @Override
    public List<Role> findOtherRoles(String userId) {
        return userDao.findOtherRoles(userId);
    }


    @Override
    public void addRoleToUser(String userId, String[] roleIds) {
        for(String roleId:roleIds){
            userDao.addRoleToUser(userId,roleId);
        }
    }

    //先删除中间表
    @Override
    public void deleteUserById(String roleId) {
        //从user_role表中删除
        userDao.deleteFromUserRoleByUserId(roleId);
        //从user表中删除
        userDao.deleteUserById(roleId);
    }
}
