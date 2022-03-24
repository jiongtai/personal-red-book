package com.imooc.service;

import com.imooc.pojo.Users;

public interface UserService {
    /**
     * 查看客户是否已经存在，若存在则返回用户信息
     * @return Users
     */
    public Users queryMobileIsExist(String mobile);

    /**
     * 创建用户，返回用户信息
     * @return Users
     */
    public Users createUser(String mobile);

    /**
     * 根据用户主键查询用户信息
     */
    public Users getUser(String userId);
}
