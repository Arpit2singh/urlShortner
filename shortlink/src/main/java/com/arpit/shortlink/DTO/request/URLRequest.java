package com.arpit.shortlink.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
public class URLRequest {

    @NotBlank 
    private String longurl ; 

    private Integer expireAt ;

    public void setLongurl(String longurl){
        this.longurl = longurl ; 
    }
    public String getLongurl(){
        return this.longurl ; 
    } 
    
    public void setExpireAt(Integer expireAt){
        this.expireAt = expireAt ; 
    }
    public Integer getExpireAt(){
        return this.expireAt ; 
    }
}
