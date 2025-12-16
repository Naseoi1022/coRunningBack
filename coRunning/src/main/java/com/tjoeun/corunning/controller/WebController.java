package com.tjoeun.corunning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // 루트
    @GetMapping("/")
    public String root() {
        return "forward:/index.html";
    }

    // 확장자(.) 없는 1-depth 경로
    @GetMapping("/{path:[^\\.]*}")
    public String forwardSingle() {
        return "forward:/index.html";
    }

    // 확장자(.) 없는 다중 depth 경로
    @GetMapping("/**/{path:[^\\.]*}")
    public String forwardMulti() {
        return "forward:/index.html";
    }
}
