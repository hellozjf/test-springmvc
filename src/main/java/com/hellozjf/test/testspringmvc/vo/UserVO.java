package com.hellozjf.test.testspringmvc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hellozjf.test.testspringmvc.dataobject.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hellozjf
 */
@Data
public class UserVO {
    private String id;
    @JsonProperty("UserName")
    private String username;
    private String password;
    private Integer age;

    public static UserVO getByUser(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    public static List<UserVO> getListByUserList(List<User> userList) {
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            UserVO userVO = getByUser(user);
            userVOList.add(userVO);
        }
        return userVOList;
    }
}
