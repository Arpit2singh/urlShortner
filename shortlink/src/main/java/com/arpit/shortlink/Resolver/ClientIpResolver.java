package com.arpit.shortlink.Resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component 
public class ClientIpResolver {
  

    public String getUserIp(HttpServletRequest request){
        return request.getRemoteAddr().toString() ;
    }
    
}
