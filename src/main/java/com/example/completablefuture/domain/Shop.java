package com.example.completablefuture.domain;

import com.example.completablefuture.utils.TimerUtil;
import lombok.Getter;

import java.util.Random;

@Getter
public class Shop {

    private long id;
    private String name;
    private Random random;

    public Shop(long id, String name){
        this.id = id;
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

}
