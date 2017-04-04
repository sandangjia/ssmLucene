package com.erinic.ssm.cxf.impl;

import com.erinic.ssm.cxf.Helloword;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * Created by asus on 2017/4/4.
 */
@Component("helloWorld")
@WebService
public class HelloWordImpl implements Helloword {

    @Override
    public String say(String str){
        return "hello " + str;
    }
}
