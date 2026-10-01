package com.arpit.shortlink.service.serviceImpl;
import com.arpit.shortlink.Entity.UrlMapping;
import com.arpit.shortlink.Repository.UrlRepository;
import com.arpit.shortlink.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arpit.shortlink.util.Base62Encoder;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import com.arpit.shortlink.GlobalExceptionHandling.InvalidUrlException;
import java.util.Optional;
import java.util.Base64.Encoder;
import com.arpit.shortlink.DTO.request.URLRequest;
import com.arpit.shortlink.util.UrlValidator;
import com.arpit.shortlink.GlobalExceptionHandling.urlNotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import java.time.Duration;


@Service 
public class UrlServiceImpl implements UrlService {
    
    @Autowired
    public UrlRepository urlRepository ;
    @Autowired 
    UrlValidator urlValidator ;

    @Autowired 
    RedisTemplate<String, String> redisTemplate ; 

    @Override
    public UrlMapping createShortUrl(URLRequest longUrl){
      
        Boolean check = urlValidator.isValid(longUrl.getLongurl()) ; 
        if(!check){
            throw new InvalidUrlException("Invalid URL format. Please provide a valid URL.");
        }
        String LongUrl = longUrl.getLongurl() ; 
        Optional<UrlMapping>UrlStruct = urlRepository.findByLongUrl(LongUrl); 
        if(UrlStruct.isPresent()){
            return UrlStruct.get() ; 
        }

        UrlMapping urlMapping = new UrlMapping() ; 
        urlMapping.setClickCount(0L);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setLongUrl(LongUrl);
        if(longUrl.getExpireAt() != null && longUrl.getExpireAt() > 0){
        urlMapping.setExpireAt(LocalDateTime.now().plusMinutes(longUrl.getExpireAt()));
        }
        urlRepository.save(urlMapping) ; 
 
        Long id = urlMapping.getId() ; 

        String encodedLong = new Base62Encoder().encode(id) ;
        urlMapping.setShortCode(encodedLong);
        urlRepository.save(urlMapping) ;
        if(longUrl.getExpireAt() != null && longUrl.getExpireAt() > 0){
            redisTemplate.opsForValue().set(encodedLong , LongUrl , Duration.ofMinutes(longUrl.getExpireAt())) ; 
        }
        else{
            redisTemplate.opsForValue().set(encodedLong , LongUrl) ; 
        }
     
        return urlMapping ;

    }

    @Override 
    public String getUrl(String shortCode){
        if(redisTemplate.hasKey(shortCode)){
            return redisTemplate.opsForValue().get(shortCode) ; 
        }
       Optional<UrlMapping> userStruct = urlRepository.findByShortCode(shortCode) ; 
       if(userStruct.isPresent()){
         LocalDateTime exp = userStruct.get().getExpireAt() ; 
            if( exp != null &&  (exp.isBefore(LocalDateTime.now()) || exp.isEqual(LocalDateTime.now()))){
                redisTemplate.delete(shortCode) ;
                 throw new RuntimeException("url is expired") ;
            }
            if (exp!=null) {
                
            
            Duration remainingTime = Duration.between(LocalDateTime.now(), exp);
            if(exp != null &&  !remainingTime.isNegative() && !remainingTime.isZero() )   redisTemplate.opsForValue().set(shortCode , userStruct.get().getLongUrl() , remainingTime) ;    
            }
        return userStruct.get().getLongUrl() ; 
       }

       else throw new urlNotFoundException("URL not found for the provided short code: " + shortCode); 
    }

}
