package com.example.cityfun.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.alibaba.druid.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * @author xiaohan
 * @since 2019-08-21 16:52
 */
public class JsonUtils {

    final static String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象字段全部列入
        objectMapper.setSerializationInclusion(NON_DEFAULT);

        // 取消默认转换timestamps形式
        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS,false);

        // 忽略空bean转json的错误
        objectMapper.configure(FAIL_ON_EMPTY_BEANS,false);

        // 统一日期格式yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));

        // 忽略在json字符串中存在,但是在java对象中不存在对应属性的情况
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    /**
     * Object转json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("Parse object to String error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Object转json字符串并格式化美化
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StringPretty(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("Parse object to String error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转object
     * @param str json字符串
     * @param clazz 被转对象class
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)? (T) str :objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            System.out.println("Parse String to Object error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转object
     * @param str json字符串
     * @param typeReference 被转对象引用类型
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if (StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str :objectMapper.readValue(str,typeReference));
        } catch (IOException e) {
            System.out.println("Parse String to Object error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转object 用于转为集合对象
     * @param str json字符串
     * @param collectionClass 被转集合class
     * @param elementClasses 被转集合中对象类型class
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            System.out.println("Parse String to Object error");
            e.printStackTrace();
            return null;
        }
    }

}
