package com.arpit.shortlink.util;

public class Base62Encoder {
    
    final String base62Chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" ; 

    public String encode(Long id){
        StringBuilder s = new StringBuilder() ; 

        while(id > 0){
            long rem = id % 62 ; 
            s.append(base62Chars.charAt((int)rem)) ; 
            id = id/62 ; 
        }


        return s.reverse().toString() ; 
    }
}
