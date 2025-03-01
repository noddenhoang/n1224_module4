package com.techzenacademy.n1224_module4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaculatorController {
    @RequestMapping("/caculator")
    public ResponseEntity<String> caculator(@RequestParam String a, @RequestParam String b, @RequestParam String operator) {
        double num1 = Double.parseDouble(a);
        double num2 = Double.parseDouble(b);
        double result = 0;
        if (operator.equals("+")) {
            result = num1 + num2;
        } else if (operator.equals("-")) {
            result = num1 - num2;
        } else if (operator.equals("*")) {
            result = num1 * num2;
        } else if (operator.equals("/")) {
            if (num2 == 0) {
                return ResponseEntity.status(400).body("Loi chia cho 0");
            } else result = num1 / num2;
        } else {
            return ResponseEntity.status(400).body("Loi cu phap");
        }
        return ResponseEntity.ok("Result: " + result);
    }
}