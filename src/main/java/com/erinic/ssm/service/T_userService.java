package com.erinic.ssm.service;

import com.erinic.ssm.domain.T_user;

import java.util.Set;

/**
 * Created by asus on 2017/4/4.
 */
public interface T_userService {

    public T_user findUserByUsername(String username) ;

    /**
     * 根据账号查找角色名称
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) ;

    /**
     * 根据账号查找权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) ;
}
