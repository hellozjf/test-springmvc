package com.hellozjf.test.testspringmvc.controller;

import com.hellozjf.test.testspringmvc.config.CustomConfig;
import com.hellozjf.test.testspringmvc.form.UserForm;
import com.hellozjf.test.testspringmvc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Part;

/**
 * @author Jingfeng Zhou
 */
@RestController
@RequestMapping("/restful")
public class RestfulController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomConfig customConfig;

    @GetMapping("/get/{id}")
    public ResultVO get(@PathVariable("id") String id) {
        ResultVO resultVO = restTemplate.getForObject(customConfig.getRestAddress() + "/user/{id}", ResultVO.class, id);
        return resultVO;
    }

    /**
     * 参见https://docs.spring.io/spring/docs/5.0.4.BUILD-SNAPSHOT/javadoc-api/org/springframework/web/client/RestTemplate.html#postForObject-java.lang.String-java.lang.Object-java.lang.Class-java.lang.Object...-
     * @param userForm
     * @return
     */
    @PostMapping("/post")
    public ResultVO post(UserForm userForm) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("username", userForm.getUsername());
        builder.part("password", userForm.getPassword());
        builder.part("age", userForm.getAge());
        builder.part("picture", new ClassPathResource("test.png"));
        MultiValueMap multiValueMap = builder.build();

        // 如果postForObject的第二个参数是MultiValueMap
        // 若map里全是String则content-type为application/x-www-form-urlencoded;charset=UTF-8
        // 否则content-type为application/form-data;charset=UTF-8
        // 如果使用MultipartBodyBuilder，需要导入rxjava，否则会报错
        ResultVO resultVO = restTemplate.postForObject(customConfig.getRestAddress() + "/user/", multiValueMap, ResultVO.class);
        return resultVO;
    }

    /**
     * 参见https://blog.csdn.net/ldy1016/article/details/80002126
     * @param userForm
     * @return
     */
    @PostMapping("/post/json")
    public ResultVO postJson(UserForm userForm) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", userForm.getUsername());
        multiValueMap.add("password", userForm.getPassword());
        multiValueMap.add("age", userForm.getAge());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multiValueMap, httpHeaders);
        ResponseEntity<ResultVO> responseEntity = restTemplate.postForEntity(customConfig.getRestAddress() + "/user/json", httpEntity, ResultVO.class);
        return responseEntity.getBody();
    }
}
