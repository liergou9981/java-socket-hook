package com.liergou.hook.socket;

import java.lang.reflect.Method;

public class Utils {

    /**
     * 轮询父类查找反射方法
     * @param inputClazz
     * @param findName
     * @param args
     * @return
     */
    public static Method findMethod(Class<?> inputClazz, String findName ,Class<?>[] args){
        Class<?> temp=inputClazz;
        Method tmpMethod = null;
        while(temp!=null){
            try{
                tmpMethod = temp.getDeclaredMethod(findName,args);
                tmpMethod.setAccessible(true);
                return tmpMethod;
            }catch (NoSuchMethodException e){
                temp=temp.getSuperclass();
            }
        }
        return null;
    }

}
