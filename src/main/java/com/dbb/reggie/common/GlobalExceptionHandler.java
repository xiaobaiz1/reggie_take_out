package com.dbb.reggie.common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 处理出现添加相同的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    private R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg =split[2]+"已存在";
            return R.error(msg);
        }
        return R.error("失败");
    }

    /**
     * 处理删除绑定异常
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    private R<String> exceptionHandler(CustomException ex){
        return R.error(ex.getMessage());
    }
}
