package com.dbb.reggie.common;

/**
 * 自定义运行异常根据自己提示来抛出异常
 * 处理运行时异常
 * 用在删除的位置

 * 例：
 * if(count1>0){
 *             throw new CustomException("当前分类项关联了菜品，不能删除");
 * }
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
