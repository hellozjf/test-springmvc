package com.hellozjf.test.testspringmvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jingfeng Zhou
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    CAN_NOT_GET_BEAN(1, "无法获取bean"),
    FORM_PARAM_ERROR(2, "form参数错误"),
    CAN_NOT_FIND_THIS_ID_OBJECT(3, "无法找到该ID对应的对象"),
    DUPLICATE_USERNAME(4, "用户名重复"),
    DUPLICATE_EMAIL(5, "邮箱重复"),
    DUPLICATE_PHONE(6, "手机号码重复"),
    CAN_NOT_ALLOC_SS_SERVER_PORT(7, "无法分配SS服务端口"),
    METHOD_RUNNING_TIME_ERROR(8, "方法运行时异常"),
    FORM_VALIDATE_ERROR(9, "表单验证失败"),
    UNCORRECT_STATE(10, "不正确的状态"),
    ;

    Integer code;
    String message;
}
