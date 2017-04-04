package com.erinic.ssm.cxf;

import javax.jws.WebService;

/**
 * Created by asus on 2017/4/4.
 */
@WebService
public interface Helloword {

    String say(String str);
}
