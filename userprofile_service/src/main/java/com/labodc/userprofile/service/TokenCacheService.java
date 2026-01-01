package com.labodc.userprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenCacheService {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    private static final String TOKEN_PREFIX = "token:";
    private static final String BLACKLIST_PREFIX = "blacklist:";
    
    public void cacheToken(String username, String token, long expirationMinutes) {
        String key = TOKEN_PREFIX + username;
        redisTemplate.opsForValue().set(key, token, expirationMinutes, TimeUnit.MINUTES);
    }
    
    public String getCachedToken(String username) {
        String key = TOKEN_PREFIX + username;
        return redisTemplate.opsForValue().get(key);
    }
    
    public void invalidateToken(String username) {
        String key = TOKEN_PREFIX + username;
        redisTemplate.delete(key);
    }
    
    public void blacklistToken(String token, long expirationMinutes) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "blacklisted", expirationMinutes, TimeUnit.MINUTES);
    }
    
    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}