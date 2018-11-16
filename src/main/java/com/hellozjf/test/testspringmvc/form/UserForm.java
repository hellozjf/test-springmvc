package com.hellozjf.test.testspringmvc.form;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author hellozjf
 */
@Data
public class UserForm implements Serializable {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @Pattern(regexp = "[0-9a-zA-Z]{6,30}", message = "密码是6-30个字符，必须是字母或数字组合")
    private String password;

    @Email(message = "邮箱不正确")
    private String email;

    @NotNull
    @Min(0)
    @Max(200)
    private Integer age;
}
