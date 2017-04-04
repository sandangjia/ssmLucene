package com.erinic.ssm.shiro;

import com.erinic.ssm.domain.T_user;
import com.erinic.ssm.service.T_userService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by asus on 2017/4/4.
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private T_userService t_userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){
        String username = principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = t_userService.findRoles(username);
        Set<String> ps = t_userService.findPermissions(username);
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(ps);
        return simpleAuthorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException{
        String username = token.getPrincipal().toString();
        T_user user = t_userService.findUserByUsername(username);
        if (user != null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"au");
            return info;
        }else {
            return null;
        }
    }


}
