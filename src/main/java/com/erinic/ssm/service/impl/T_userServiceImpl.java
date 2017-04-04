package com.erinic.ssm.service.impl;

import com.erinic.ssm.dao.T_userDao;
import com.erinic.ssm.domain.T_user;
import com.erinic.ssm.service.T_userService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by asus on 2017/4/4.
 */
@Service
public class T_userServiceImpl implements T_userService {

    @Resource
    private T_userDao t_userDao;

    @Override
    public T_user findUserByUsername(String username) {
        T_user t_user = t_userDao.findUserByUsername(username);
        return t_user;
    }

    @Override
    public Set<String> findRoles(String username) {
        return t_userDao.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return t_userDao.findPermissions(username);
    }
}
