package com.arpit.shortlink.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arpit.shortlink.Repository.UrlRepository ;
import jakarta.transaction.Transactional;

@Service 
@Transactional 
public class IncreaseClickCount {
    @Autowired 
    private UrlRepository urlRepository ;
    public void increaseClickCount(String shortCode){
        urlRepository.increaseClickCount(shortCode); 
    }
}
