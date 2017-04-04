package com.erinic.ssm.dao;

import com.erinic.ssm.domain.T_user;

import java.util.Set;

/**
 * Created by asus on 2017/4/4.
 */
public interface T_userDao {

    T_user findUserByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
