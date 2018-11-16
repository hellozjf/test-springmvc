package com.hellozjf.test.testspringmvc.controller;

import com.hellozjf.test.testspringmvc.config.CustomConfig;
import com.hellozjf.test.testspringmvc.constant.ResultEnum;
import com.hellozjf.test.testspringmvc.dataobject.User;
import com.hellozjf.test.testspringmvc.exception.SpringMVCException;
import com.hellozjf.test.testspringmvc.form.UserForm;
import com.hellozjf.test.testspringmvc.repository.UserRepository;
import com.hellozjf.test.testspringmvc.util.PartUtils;
import com.hellozjf.test.testspringmvc.util.ResultUtils;
import com.hellozjf.test.testspringmvc.vo.ResultVO;
import com.hellozjf.test.testspringmvc.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.util.List;

/**
 * @author hellozjf
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomConfig customConfig;

    @GetMapping("/")
    public ResultVO get() {
        List<User> userList = userRepository.findAll();
        List<UserVO> userVOList = UserVO.getListByUserList(userList);
        return ResultUtils.success(userVOList);
    }

    @GetMapping("/{id}")
    public ResultVO get(@PathVariable("id") String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new SpringMVCException(ResultEnum.CAN_NOT_FIND_THIS_ID_OBJECT));
        UserVO userVO = UserVO.getByUser(user);
        return ResultUtils.success(userVO);
    }

    @PostMapping("/")
    public ResultVO post(@Valid UserForm userForm,
                         @RequestPart(name = "picture", required = false) Part picture,
                         Errors errors) {

        if (errors.hasErrors()) {
            log.error("表单验证错误 {}", errors.getAllErrors());
            return ResultUtils.error(ResultEnum.FORM_VALIDATE_ERROR);
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        if (picture != null) {
            user.setPicture(PartUtils.getPartByteArray(picture));
            user.setPictureName(picture.getSubmittedFileName());
        }
        user = userRepository.save(user);
        UserVO userVO = UserVO.getByUser(user);
        return ResultUtils.success(userVO);
    }

    @PostMapping("/json")
    public ResultVO postJson(@Valid @RequestBody UserForm userForm,
                             Errors errors) {

        if (errors.hasErrors()) {
            log.error("表单验证错误 {}", errors.getAllErrors());
            return ResultUtils.error(ResultEnum.FORM_VALIDATE_ERROR);
        }

        return post(userForm, null, errors);
    }

    @PutMapping("/{id}")
    public ResultVO put(@PathVariable("id") String id,
                        @RequestPart(name = "picture", required = false) Part picture,
                        @Valid UserForm userForm,
                        Errors errors) {

        if (errors.hasErrors()) {
            log.error("表单验证错误 {}", errors.getAllErrors());
            return ResultUtils.error(ResultEnum.FORM_VALIDATE_ERROR);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new SpringMVCException(ResultEnum.CAN_NOT_FIND_THIS_ID_OBJECT));
        BeanUtils.copyProperties(userForm, user);
        if (picture != null) {
            user.setPicture(PartUtils.getPartByteArray(picture));
            user.setPictureName(picture.getSubmittedFileName());
        }

        user = userRepository.save(user);
        UserVO userVO = UserVO.getByUser(user);
        return ResultUtils.success(userVO);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {
        userRepository.deleteById(id);
        return ResultUtils.success();
    }
}
