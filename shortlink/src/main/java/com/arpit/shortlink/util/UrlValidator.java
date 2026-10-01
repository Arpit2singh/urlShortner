package com.arpit.shortlink.util;

import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component 
public class UrlValidator {

    public boolean isValid(String url){
         URI Url = null ;
        try{
             Url = URI.create(url) ; 
        }
        catch(Exception e){
           return false ;
        }
       
        String scheme = Url.getScheme() ; 
        String host = Url.getHost() ; 
        if(scheme != null && host != null && (  scheme.equals("http") || scheme.equals("https" ))){
            return true ; 
        }
        return false ;
    }
}
