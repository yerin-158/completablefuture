package com.example.completablefuture.service;

import com.example.completablefuture.domain.Shop;
import com.example.completablefuture.utils.RandomUtil;
import com.example.completablefuture.utils.TimerUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopService {

    public Map<String, Object> getSyncPrice(){
        Map<String, Object> response = new HashMap<>();

        long beforeTime = System.currentTimeMillis();

        List<Map<Long, Double>> prices = new ArrayList<>();
        for (int i = 0; i < 10; ++i){
            Shop shop = new Shop(i, RandomUtil.getRandomString(5));
            Double price = getSyncPrice(shop, RandomUtil.getRandomString(3));
            prices.add(Map.of(shop.getId(), price));
        }

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산

        response.put("prices", prices);
        response.put("executionTime", secDiffTime);

        return response;
    }

    private double getSyncPrice(Shop shop, String product){
        return calculatePrice(shop.getRandom(), product);
    }

    private double calculatePrice(Random random, String product){
        TimerUtil.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
