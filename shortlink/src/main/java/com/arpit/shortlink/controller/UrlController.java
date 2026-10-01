package com.arpit.shortlink.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;
import com.arpit.shortlink.service.UrlService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import com.arpit.shortlink.DTO.request.URLRequest;
import com.arpit.shortlink.Entity.UrlMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.servlet.view.RedirectView;
import jakarta.validation.Valid;


@RestController 
@CrossOrigin 
public class UrlController {
    
    @Autowired
    private UrlService urlService ;

    @PostMapping("/api/shorten")
    public UrlMapping shortenUrl(@Valid @RequestBody URLRequest longUrl){
       return urlService.createShortUrl(longUrl) ;
    }

    @GetMapping("/get/longUrl/{shortCode}")
    public String getLongUrl(@PathVariable String shortCode){
        String longUrl = urlService.getUrl(shortCode) ;
        return longUrl ; 
    }
    @GetMapping("/go/longUrl/{shortCode}")
        public RedirectView goLongUrl(@PathVariable String shortCode){
        String longUrl = urlService.getUrl(shortCode) ;
        return new RedirectView(longUrl) ; 
    }
}   
