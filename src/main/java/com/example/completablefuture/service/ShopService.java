package com.example.completablefuture.service;

import com.example.completablefuture.domain.Shop;
import com.example.completablefuture.utils.RandomUtil;
import com.example.completablefuture.utils.TimerUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopService {

    public Map<String, Object> getSyncPrice(){
        long beforeTime = System.currentTimeMillis();
        List<Map<Long, Double>> prices = getPrices();
        long afterTime = System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("prices", prices);
        response.put("executionTime", (afterTime - beforeTime)/1000);

        return response;
    }

    private List<Map<Long, Double>> getPrices() {
        List<Map<Long, Double>> prices = new ArrayList<>();
        for (int i = 0; i < 10; ++i){
            Shop shop = new Shop(i, RandomUtil.getRandomString(5));
            Double price = getSyncPrice(shop, RandomUtil.getRandomString(3));
            prices.add(Map.of(shop.getId(), price));
        }
        return prices;
    }

    private double getSyncPrice(Shop shop, String product){
        return calculatePrice(shop.getRandom(), product);
    }

    private double calculatePrice(Random random, String product){
        TimerUtil.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
