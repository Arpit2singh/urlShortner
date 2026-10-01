package com.arpit.shortlink.service;
import com.arpit.shortlink.DTO.request.URLRequest;
import com.arpit.shortlink.Entity.UrlMapping;
public interface UrlService {
    UrlMapping createShortUrl(URLRequest longUrl  );
    String getUrl(String shortCode) ; 
}
