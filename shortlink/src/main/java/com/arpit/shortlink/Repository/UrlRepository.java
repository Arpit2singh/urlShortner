package com.arpit.shortlink.Repository;
import com.arpit.shortlink.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping , Long>{
      Optional<UrlMapping>findByShortCode(String code) ; 
      Optional<UrlMapping>findByLongUrl(String longUrl) ; 

}
