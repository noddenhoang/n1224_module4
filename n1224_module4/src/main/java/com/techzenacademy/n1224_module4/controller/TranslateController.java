package com.techzenacademy.n1224_module4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TranslateController {

    Map<String, String> dictionary = Map.of(
            "hello", "xin chào",
            "goodbye", "tạm biệt",
            "love", "yêu",
            "hate", "ghét",
            "happy", "hạnh phúc",
            "sad", "buồn",
            "angry", "tức giận",
            "excited", "phấn khích",
            "tired", "mệt",
            "sleepy", "buồn ngủ"
    );

    @RequestMapping("/translate")
    public ResponseEntity<String> translate(@RequestParam(defaultValue = "") String word) {

        if (dictionary.containsKey(word.trim().toLowerCase())) {
            return ResponseEntity.ok(dictionary.get(word.trim().toLowerCase()));
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy từ trong từ điển");
        }
    }
}