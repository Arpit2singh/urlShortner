package com.arpit.shortlink.config;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "rate-limit")
public class RateLimiterProperties {
    
    private long capacity;
    private long requestCost ; 
    private long refillRate ; 


    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
    public long getCapacity() {
        return capacity;
    }
     public void setRequestCost(long    requestCost) {
        this.requestCost = requestCost;
    }
    public long getRequestCost() {
        return requestCost;
    }
    public long getRefillRate() {
        return refillRate;
    }
    public void setRefillRate(long refillRate) {
        this.refillRate = refillRate;
    }

}
