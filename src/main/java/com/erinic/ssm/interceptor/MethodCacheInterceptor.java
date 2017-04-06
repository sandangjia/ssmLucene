package com.erinic.ssm.interceptor;

import com.erinic.ssm.utils.RedisUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/4/6.
 */
public class MethodCacheInterceptor implements MethodInterceptor{
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private RedisUtil redisUtil;
    private List<String> excludeName ;
    private List<String> excludeMethod;
    private Long defaultCacheExpireTime; // 缓存默认的过期时间
    private Long xxxRecordManagerTime; //
    private Long xxxSetRecordManagerTime; //

    public MethodCacheInterceptor(){
        try{
            String[] names = {};
            String[] methods = {};

            defaultCacheExpireTime = 3600L;
            xxxRecordManagerTime = 60L;
            xxxSetRecordManagerTime = 60L;

            excludeMethod = new ArrayList<String>(methods.length);
            excludeName = new ArrayList<String>(names.length);
            Integer maxLength = names.length > methods.length ? names.length : methods.length;
            for (int i = 0; i < maxLength; i++){
                if (i < names.length){
                    excludeName.add(names[i]);
                }
                if (i < methods.length){
                    excludeMethod.add(methods[i]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(MethodInvocation var1) throws Throwable{

        Object value = null;

        String methodName = var1.getMethod().getName();
        String className = var1.getMethod().getClass().getName();
        if (!isAddCache(methodName,className)){
            return var1.proceed();
        }
        Object[] arguments = var1.getArguments();
        String key = getCacheKey(className,methodName,arguments);
        logger.debug("redisKey: " + key);
        try{
            if (redisUtil.exits(key)){
                return redisUtil.get(key);
            }
            value = var1.proceed();
            if (value != null){
                final String ik = key;
                Object kv = value;
                new Thread(new Runnable()  {
                    @Override
                    public void run() {
                        if (ik.startsWith("com.service.impl.xxxRecordManager")) {
                            redisUtil.set(ik, kv, xxxRecordManagerTime);
                        } else if (ik.startsWith("com.service.impl.xxxSetRecordManager")) {
                            redisUtil.set(ik, kv, xxxSetRecordManagerTime);
                        } else {
                            redisUtil.set(ik, kv, defaultCacheExpireTime);
                        }
                    }
                }).start();
            }
        }catch (Exception e){
            e.printStackTrace();
            if (value == null){
                value = var1.proceed();
            }
        }

        return value;
    }

    public boolean isAddCache(String methodName,String className){
        return !excludeMethod.contains(methodName) && !excludeName.contains(className);
    }

    private String getCacheKey(String targetName, String methodName,
                               Object[] arguments) {
        StringBuffer sbu = new StringBuffer();
        sbu.append(targetName).append("_").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sbu.append("_").append(arguments[i]);
            }
        }
        return sbu.toString();
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
