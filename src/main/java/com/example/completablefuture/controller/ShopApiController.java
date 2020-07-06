package com.example.completablefuture.controller;

import com.example.completablefuture.domain.RequestType;
import com.example.completablefuture.service.HttpContactService;
import com.example.completablefuture.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class ShopApiController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private HttpContactService httpContactService;

    @GetMapping("/")
    public ResponseEntity<String> connectTest() {
        return ResponseEntity.ok("success connecting");
    }

    @GetMapping("/price")
    public ResponseEntity<Map> getPrice(@RequestParam("type") RequestType type) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(shopService.getPrice(type));
    }

    @GetMapping("/read")
    public ResponseEntity<String> readHttpSession() throws ExecutionException, InterruptedException {
        // cf로 생성된 thread에서 부모 스레드인 request의 정보를 읽을 수 있도록 한다.
        httpContactService.read();
        return ResponseEntity.ok("read success!!");
    }

    @GetMapping("/v2/read")
    public ResponseEntity<String> readHttpSession2() throws ExecutionException, InterruptedException {
        // cf로 생성된 thread에서 부모 스레드인 request의 정보를 읽을 수 있도록 한다.
        httpContactService.readWithLocalVariable();
        return ResponseEntity.ok("read2 success!!");
    }

    @GetMapping("/v3/read")
    public ResponseEntity<String> readHttpSession3() throws ExecutionException, InterruptedException {
        // cf로 생성된 thread에서 부모 스레드인 request의 정보를 읽을 수 있도록 한다.
        httpContactService.readByLocalSetting();
        return ResponseEntity.ok("read3 success!!");
    }

}
