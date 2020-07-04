package com.example.completablefuture.controller;

import com.example.completablefuture.domain.RequestType;
import com.example.completablefuture.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/")
    public ResponseEntity<String> connectTest(){
        return ResponseEntity.ok("success connecting");
    }

    @GetMapping("/price")
    public ResponseEntity<Map> getPrice(@RequestParam("type") RequestType type) {
        if (type.equals(RequestType.sync)){
            return ResponseEntity.ok(shopService.getSyncPrice());
        }

        return null;
    }

}