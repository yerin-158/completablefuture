package com.example.completablefuture.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class HttpContactService {

    Executor executor = Executors.newFixedThreadPool(10);

    public void read() throws ExecutionException, InterruptedException {
        log.info(" >>>> read 메소드 접근");
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info(" >>>> Authorization : "+req.getHeader("Authorization"));

        CompletableFuture<String> future = getAuthorization();
        String authorization = future.get();

        log.info(" >>>> completableFuture result : "+authorization);
    }

    public void readWithLocalVariable() throws ExecutionException, InterruptedException {
        log.info(" >>>> read 메소드 접근");
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info(" >>>> Authorization : "+req.getHeader("Authorization"));

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> req.getHeader("Authorization"));

        String authorization = future.get();

        log.info(" >>>> completableFuture result : "+authorization);
    }

    public void readByLocalSetting() throws ExecutionException, InterruptedException {
        log.info(" >>>> read 메소드 접근");
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info(" >>>> Authorization : "+req.getHeader("Authorization"));

        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        CompletableFuture<String> future = getAuthorization();

        String authorization = future.get();

        log.info(" >>>> completableFuture result : "+authorization);
    }

    private CompletableFuture<String> getAuthorization() {
        return CompletableFuture.supplyAsync(()->{
            log.info(" 새로운 쓰레드로 작업 ");
            HttpServletRequest req = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            log.info(" 작업 성공 ");
            return req.getHeader("Authorization");
        }, executor);
    }

}
