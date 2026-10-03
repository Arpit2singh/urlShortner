package com.arpit.shortlink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.arpit.shortlink.Model.UserRateLimitDetails ;
import jakarta.servlet.http.HttpServletRequest;
import com.arpit.shortlink.Resolver.ClientIpResolver;
import com.arpit.shortlink.config.RateLimiterProperties;

import java.time.Duration;
import java.time.LocalDateTime;


@Service 
public class RateLimiterService {
    @Autowired 
    private  ClientIpResolver clientIpResolver ;
    @Autowired 
    RedisTemplate<String , String>redisTemplate  ;
    @Autowired 
    RateLimiterProperties rateLimiterProperties ; 

    
    public boolean HandlingIpAddress(HttpServletRequest request){
      
        String ipAddress = clientIpResolver.getUserIp(request) ;
        System.out.println("IP Address: " + ipAddress);
        UserRateLimitDetails userRateLimitDetails = new UserRateLimitDetails() ; 
        String key = "rate_limit:" + ipAddress;
        // userRateLimitDetails.setuserRequestLeft(5L);
        // userRateLimitDetails.setuserLastTimeRefresh(LocalDateTime.now());

        if(redisTemplate.opsForHash().get(key , "tokenLeft") == null){
        String tokenLeft = String.valueOf(rateLimiterProperties.getCapacity()) ; 
        String lastRefreshTime = LocalDateTime.now().toString() ;
        redisTemplate.opsForHash().put(key , "tokenLeft" , tokenLeft) ;
        redisTemplate.opsForHash().put(key , "lastRefreshTime" , lastRefreshTime) ;
            
        }
        else{
            Object tokenLeft = redisTemplate.opsForHash().get(key , "tokenLeft") ; 
            Object lastRefreshTime = redisTemplate.opsForHash().get(key , "lastRefreshTime") ; 
            System.out.println("tokenLeft "+ tokenLeft); 
            System.out.println("lastRefreshTime " + lastRefreshTime);

        Duration elapsedTime = Duration.between( LocalDateTime.parse(lastRefreshTime.toString()) , LocalDateTime.now()) ; 
            long TotalRefreshToken = elapsedTime.getSeconds() * rateLimiterProperties.getRefillRate() ; 
            long currentToken  = Long.parseLong(String.valueOf(redisTemplate.opsForHash().get(key, "tokenLeft"))) ; 
            redisTemplate.opsForHash().put(key, "lastRefreshTime" , LocalDateTime.now().toString());
            long newToken = Math.min(currentToken + TotalRefreshToken , rateLimiterProperties.getCapacity()) ;
            System.out.println("newToken: " + newToken);
            if(newToken>= rateLimiterProperties.getRequestCost()){
                redisTemplate.opsForHash().put(key, "tokenLeft" , String.valueOf(newToken - rateLimiterProperties.getRequestCost())) ; 
            }
            else{
                throw new RuntimeException("Rate limit exceeded. Please try again later.");
            }
        }
        return true ; 
    }
}
