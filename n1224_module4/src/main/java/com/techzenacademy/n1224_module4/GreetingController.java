package com.techzenacademy.n1224_module4;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(defaultValue = "") String name) {
        return "Hello " + name + "!!!";
    }
}
