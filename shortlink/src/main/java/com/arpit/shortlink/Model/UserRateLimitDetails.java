package com.arpit.shortlink.Model;

import java.time.LocalDateTime;

public class UserRateLimitDetails {
    private Long userRequestLeft ;
    private LocalDateTime userLastTimeRefresh ; 
    
    public void setuserRequestLeft(Long n){
        this.userRequestLeft =  n ; 
    }
    public Long getuserRequestLeft(){
        return this.userRequestLeft ;
    }
    public void setuserLastTimeRefresh(LocalDateTime t){
        this.userLastTimeRefresh = t ; 
    }  
    public LocalDateTime getuserLastTimeRefresh(){
        return this.userLastTimeRefresh ; 
    }
}
