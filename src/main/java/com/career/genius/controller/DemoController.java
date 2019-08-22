package com.career.genius.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-08-22 08:52
 * @discription
 **/
@Controller
public class DemoController {

    @GetMapping(value = "/demo")
    public String demo(ModelMap map) {
//        map.addAttribute("userName", "Catalina");
        map.put("params", "appid=wx5f6a7488cc0f5a36&userName=Catalina");
        return "index";
    }
}
