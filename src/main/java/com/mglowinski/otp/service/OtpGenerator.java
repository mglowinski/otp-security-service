package com.mglowinski.otp.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpGenerator {

    private static final int EXPIRE_MIN = 1;
    private LoadingCache<String, Integer> otpCache;

    public OtpGenerator() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    int generateOtp(String username) {
        Random random = new Random();

        int otp = 100000 + random.nextInt(900000);
        otpCache.put(username, otp);

        return otp;
    }

    int getUserOtp(String username) {
        try {
            return otpCache.get(username);
        } catch (ExecutionException e) {
            return -1;
        }
    }

    void clearOtpFromCache(String username) {
        otpCache.invalidate(username);
    }

}
