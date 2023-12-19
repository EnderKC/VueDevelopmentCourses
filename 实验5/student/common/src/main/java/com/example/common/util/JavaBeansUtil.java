package com.example.common.util;

/**
 * java bean工具类
 *
 * @author lihuanzhe
 * @version 1.0
 */
public class JavaBeansUtil {
    /**
     * 根据属性名称和java类型，获取对应的getter方法名
     * @param property 属性名称
     * @param javaType java类型
     * @return 返回getter方法名
     */
    public static String getGetterMethodName(String property, String javaType) {
        StringBuilder sb = new StringBuilder();
        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }
        if ("boolean".equals(javaType)) {
            sb.insert(0, "is");
        } else {
            sb.insert(0, "get");
        }
        return sb.toString();
    }
    /**
     * 根据属性名称获取对应的setter方法名称
     * @param property 属性名称
     * @return  返回setter方法名
     */
    public static String getSetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();
        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }
        sb.insert(0, "set");
        return sb.toString();
    }

}
