package com.arpit.shortlink.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.Column;


@Entity
@Table(name = "url_mapping")
public class UrlMapping {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(columnDefinition = "TEXT" , nullable = false) 
    private String longUrl ; 

    @Column(unique=true)
    private String shortCode ;

    private LocalDateTime createdAt ; 
    private LocalDateTime expireAt ;
    private LocalDateTime lastAccessAt ; 

    private Long clickCount = 0L ; 

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
    public String getLongUrl() {
        return longUrl;
    }
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
    public String getShortCode() {
        return shortCode;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }
    public LocalDateTime getExpireAt() {
        return expireAt;
    }
    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }
    public Long getClickCount() {
        return clickCount;
    }

    public void setLastAccessAt(LocalDateTime lastAccessAt) {
        this.lastAccessAt = lastAccessAt;
    }
    public LocalDateTime getLastAccessAt() {
        return lastAccessAt;
    }
    
}
