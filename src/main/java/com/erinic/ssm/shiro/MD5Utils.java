package com.erinic.ssm.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by asus on 2017/4/4.
 */
public class MD5Utils {

    public static String md5(String str,String slat){
        return new Md5Hash(str,slat).toString();
    }

    public static void main(String[] args) {
        String md5 = md5("abc123","erinic") ;
        System.out.println(md5);
    }
}
