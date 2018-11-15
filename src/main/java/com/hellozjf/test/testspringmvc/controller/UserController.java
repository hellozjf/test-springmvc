package com.hellozjf.test.testspringmvc.controller;

import com.hellozjf.test.testspringmvc.config.CustomConfig;
import com.hellozjf.test.testspringmvc.constant.ResultEnum;
import com.hellozjf.test.testspringmvc.dataobject.User;
import com.hellozjf.test.testspringmvc.exception.SpringMVCException;
import com.hellozjf.test.testspringmvc.form.UserForm;
import com.hellozjf.test.testspringmvc.repository.UserRepository;
import com.hellozjf.test.testspringmvc.util.ResultUtils;
import com.hellozjf.test.testspringmvc.vo.ResultVO;
import com.hellozjf.test.testspringmvc.vo.UserVO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.InputStream;
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
    public ResultVO post(UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user = userRepository.save(user);
        UserVO userVO = UserVO.getByUser(user);
        return ResultUtils.success(userVO);
    }

    @PostMapping("/json")
    public ResultVO postJson(@RequestBody UserForm userForm) {
        return post(userForm);
    }

    @PutMapping("/{id}")
    public ResultVO put(@PathVariable("id") String id,
                        @RequestPart("picture") Part part,
                        UserForm userForm) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new SpringMVCException(ResultEnum.CAN_NOT_FIND_THIS_ID_OBJECT));

        BeanUtils.copyProperties(userForm, user);

        try (InputStream in = part.getInputStream()) {
            ByteBuf byteBuf = Unpooled.buffer();
            log.debug("maxCapacity = {}", byteBuf.maxCapacity());
            byte[] bytes = byteBuf.array();
            int totalReadNum = 0;
            int readNum = 0;
            while ((readNum = in.read(bytes, totalReadNum, 1024)) > 0) {
                totalReadNum += readNum;
            }
            user.setPicture(byteBuf.array());
        } catch (Exception e) {
            log.error("error = {}", e);
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
