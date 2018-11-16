package com.hellozjf.test.testspringmvc.controller;

import com.alibaba.fastjson.JSONObject;
import com.hellozjf.test.testspringmvc.config.CustomConfig;
import com.hellozjf.test.testspringmvc.constant.ResultEnum;
import com.hellozjf.test.testspringmvc.form.TokenQueryForm;
import com.hellozjf.test.testspringmvc.util.ResultUtils;
import com.hellozjf.test.testspringmvc.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jingfeng Zhou
 */
@Controller
@RequestMapping("/sso")
@Slf4j
public class SSOController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomConfig customConfig;

    private String clientId;
    private String clientSecret;
    private String state;
    private String redirectUri;

    @RequestMapping("/login")
    public ModelAndView login(String clientId, String clientSecret) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.state = "Hello";
        this.redirectUri = customConfig.getRedirectAddress() + "/sso/redirect_uri";

//        MultipartBodyBuilder builder = new MultipartBodyBuilder();
//        builder.part("client_id", clientId);
//        builder.part("response_type", "code");
//        builder.part("redirect_uri", REDIRECT_URI);
//        builder.part("state", STATE);
//        MultiValueMap multiValueMap = builder.build();
//        String result = restTemplate.postForObject(customConfig.getSsoAddress() + "/wtzzy", multiValueMap, String.class);
//        log.debug("result = {}", result);
//        return ResultUtils.success();

        Map<String, Object> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("response_type", "code");
        map.put("redirect_uri", redirectUri);
        map.put("state", state);

        return new ModelAndView(new RedirectView(customConfig.getSsoAddress() + "/wtzzy"), map);
    }

    @GetMapping("/redirect_uri")
    @ResponseBody
    public ResultVO redirectUri(String code, String state) {
        log.debug("code={} state={}", code, state);

        if (!state.equals(this.state)) {
            log.error("state error! we:{} theirs:{}", this.state, state);
            return ResultUtils.error(ResultEnum.UNCORRECT_STATE);
        }

        // 使用 authCode 换取 accessToken
        TokenQueryForm tokenQueryForm = new TokenQueryForm();
        tokenQueryForm.setClientId(this.clientId);
        tokenQueryForm.setClientSecret(this.clientSecret);
        tokenQueryForm.setCode(code);
        tokenQueryForm.setGrantType("authorization_code");
        tokenQueryForm.setRedirectUrl(redirectUri);
        String result = restTemplate.postForObject(customConfig.getSsoAddress() + "/token/query", tokenQueryForm, String.class);
        log.debug("/token/query receive {}", result);

        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        String accessToken = data.getString("access_token");

        // 根据 accessToken 获取用户信息
        result = restTemplate.getForObject(customConfig.getSsoAddress() + "/userinfo/query?accessToken=" + accessToken, String.class);
        log.debug("userinfo/query receive {}", result);

        jsonObject = JSONObject.parseObject(result);
        data = jsonObject.getJSONObject("data");
        log.debug("xm={}", data.getString("xm"));
        log.debug("sfzjlxDm={}", data.getString("sfzjlxDm"));
        log.debug("sfzjhm={}", data.getString("sfzjhm"));
        log.debug("gjhdqszDm={}", data.getString("gjhdqszDm"));
        log.debug("zrrdah={}", data.getString("zrrdah"));

        return ResultUtils.success(data);
    }
}
