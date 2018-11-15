package com.hellozjf.test.testspringmvc.controller;

import com.hellozjf.test.testspringmvc.exception.SpringMVCException;
import com.hellozjf.test.testspringmvc.util.ResultUtils;
import com.hellozjf.test.testspringmvc.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 廖师兄
 * 2017-01-21 13:59
 */
@Slf4j
@ControllerAdvice
public class SpringMVCExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handle(Exception e) {
        if (e instanceof SpringMVCException) {
            SpringMVCException SpringMVCException = (SpringMVCException) e;
            return ResultUtils.error(SpringMVCException.getCode(), SpringMVCException.getMessage());
        } else {
            log.error("【系统异常】{}", e);
            return ResultUtils.error(-1, "未知错误");
        }
    }
}
