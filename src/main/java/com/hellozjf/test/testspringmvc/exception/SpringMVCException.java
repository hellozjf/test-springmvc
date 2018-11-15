package com.hellozjf.test.testspringmvc.exception;

import com.hellozjf.test.testspringmvc.constant.ResultEnum;
import lombok.Getter;

/**
 * @author Jingfeng Zhou
 */
@Getter
public class SpringMVCException extends RuntimeException {

    private Integer code;

    public SpringMVCException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SpringMVCException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
