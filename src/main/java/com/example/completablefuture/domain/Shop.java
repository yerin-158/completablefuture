package com.example.completablefuture.domain;

import com.example.completablefuture.utils.TimerUtil;

import java.util.Random;

public class Shop {

    private String name;
    private Random random;

    public Shop(String name){
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public double getPrice(String product){
        return calculatePrice(product);
    }

    private double calculatePrice(String product){
        TimerUtil.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public String getName() {
        return name;
    }




}
