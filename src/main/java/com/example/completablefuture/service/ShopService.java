package com.example.completablefuture.service;

import com.example.completablefuture.domain.RequestType;
import com.example.completablefuture.domain.Shop;
import com.example.completablefuture.utils.RandomUtil;
import com.example.completablefuture.utils.TimerUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ShopService {

    public Map<String, Object> getPrice(RequestType type) throws ExecutionException, InterruptedException {
        long beforeTime = System.currentTimeMillis();
        List<Map<Long, Double>> prices = type.equals(RequestType.sync) ? getPricesSync() : getPricesAsync();
        long afterTime = System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("prices", prices);
        response.put("executionTime", (afterTime - beforeTime) / 1000);

        return response;
    }

    private List<Map<Long, Double>> getPricesSync() {
        List<Map<Long, Double>> prices = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            Shop shop = new Shop(i, RandomUtil.getRandomString(5));
            Double price = getPrice(shop, RandomUtil.getRandomString(3));
            prices.add(Map.of(shop.getId(), price));
        }
        return prices;
    }

    private List<Map<Long, Double>> getPricesAsync() throws ExecutionException, InterruptedException {
        List<Map<Long, Double>> prices = new ArrayList<>();
        List<Future<Double>> priceFutures = getPriceFutures();

        for (int i = 0; i < 10; ++i) {
            prices.add(Map.of((long) i, priceFutures.get(i).get()));
        }

        return prices;
    }

    private List<Future<Double>> getPriceFutures() {
        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            Shop shop = new Shop(i, RandomUtil.getRandomString(5));
            futures.add(getAsyncPrice(shop, RandomUtil.getRandomString(3)));
        }
        return futures;
    }

    private Future<Double> getAsyncPrice(Shop shop, String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(
                () -> {
                    double price = calculatePrice(shop.getRandom(), product);
                    futurePrice.complete(price);
                }
        ).start();
        return futurePrice;
    }

    private double getPrice(Shop shop, String product) {
        return calculatePrice(shop.getRandom(), product);
    }

    private double calculatePrice(Random random, String product) {
        TimerUtil.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
