package com.arpit.shortlink.Repository;
import com.arpit.shortlink.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; 
import java.util.Optional;



public interface UrlRepository extends JpaRepository<UrlMapping , Long>{
      Optional<UrlMapping>findByShortCode(String code) ; 
      Optional<UrlMapping>findByLongUrl(String longUrl) ; 
      
      @Modifying 
      @Query("""
                update UrlMapping u set u.clickCount  = u.clickCount + 1 ,
                u.lastAccessAt = CURRENT_TIMESTAMP where u.shortCode = :shortCode  
                  """)
      int increaseClickCount(@Param("shortCode") String shortCode) ;
}
