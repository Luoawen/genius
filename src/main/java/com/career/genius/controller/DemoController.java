package com.career.genius.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        map.put("userName", "Catalina");
        return "index.html";
    }
}
