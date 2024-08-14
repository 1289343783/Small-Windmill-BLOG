package com.wublog.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;

public class ObjectUtils {
    public static Object getFieldValueByObject(Object object, String targetFieldName) throws Exception {
        // 获取该对象的Class
        Class<?> objClass = object.getClass();
        // 初始化返回值
        Object result = null;

        // 获取所有的属性数组
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            // 属性名称
            String currentFieldName = "";

            try {
                currentFieldName = field.getName();

                // 判断属性名称是否等于目标属性名称
                if (currentFieldName.equals(targetFieldName)) {
                    field.setAccessible(true);
                    result = field.get(object);
                    System.out.println("属性: " + currentFieldName + " 值: " + result);
                    if (result == null) {
                        result = 1L;
                    }
                    return result; // 通过反射拿到该属性在此对象中的值(也可能是个对象)
                }
            } catch (SecurityException e) {
                // 安全性异常
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // 非法参数
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // 无访问权限
                e.printStackTrace();
            }
        }

        return result;
    }
}
