package com.example.feng01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class EchartsController {

    @RequestMapping("/getData")
    @ResponseBody
    public String[] getFromHdfs(HttpServletRequest request) throws IOException {

        //from hadoop
        String str[] = {"10", "20", "30", "40", "50", "60"};
        return str;
    }
}
