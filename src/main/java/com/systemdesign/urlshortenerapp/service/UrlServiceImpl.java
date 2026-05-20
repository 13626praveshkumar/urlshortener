package com.systemdesign.urlshortenerapp.service;

import com.google.common.hash.Hashing;
import com.systemdesign.urlshortenerapp.model.Url;
import com.systemdesign.urlshortenerapp.model.UrlDto;
import com.systemdesign.urlshortenerapp.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class UrlServiceImpl implements UrlService{

    @Autowired
   private UrlRepository urlRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Url generateShortUrl(UrlDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getUrl()))
        {
            String encodedUrl=encodedUrl(urlDto.getUrl());
            Url urlToPersist=new Url();
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setShortLink(encodedUrl);
            urlToPersist.setCreatedDate(LocalDateTime.now());
            urlToPersist.setExpirationDateTime(getExpirationDate(urlDto.getExpiryDateTime(),urlToPersist.getCreatedDate()));
            Url urlToRet=persistShortUrl(urlToPersist);
            System.out.println("urlToPersist :"+urlToPersist);
            System.out.println("urlToRet :"+urlToRet);
            if(urlToRet!=null)
                return urlToRet;
            return null;
        }
        return null;
    }

    private LocalDateTime getExpirationDate(String expiryDateTime, LocalDateTime createdDate) {
        if(StringUtils.isBlank(expiryDateTime))
        {
           return createdDate.plusSeconds(60);
        }
        LocalDateTime expirationDateToRet=LocalDateTime.parse(expiryDateTime);
        return  expirationDateToRet;
    }


    private String encodedUrl(String url) {
        String encodedUrl="";
        LocalDateTime time=LocalDateTime.now();
        encodedUrl= Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
        return encodedUrl;
    }

    @Override
    public Url persistShortUrl(Url url) {
        Url urlToRet=urlRepository.save(url);
        System.out.println("response from DB :"+urlToRet);
        return urlToRet;
    }

    @Override
   // @Cacheable(value = "short-url", key = "#url")
    public String getEncodedUrl(String shortUrl) {
        String redisKey = "url:" + shortUrl;

        // 1. Redis lookup
        String cachedUrl =
                redisTemplate.opsForValue().get(redisKey);

        if (cachedUrl != null) {

            System.out.println("Returned from Redis");

            return cachedUrl;
        }

        // 2. DB lookup
        System.out.println("Fetching from DB");

        Url url =
                urlRepository.findByShortLink(shortUrl);

        if (url == null) {
            return null;
        }

        // 3. Dynamic TTL
//        Duration ttl = Duration.between(
//                LocalDateTime.now(),
//                url.getExpirationDateTime()
//        );

        // 4. Store in Redis
        redisTemplate.opsForValue().set(
                redisKey,
                url.getOriginalUrl(),
                Duration.ofSeconds(20)
        );

        return url.getOriginalUrl();

        // return urlRepository.findByShortLink(url);

    }

    @Override
    public void deleteShortUrl(Url url) {
       urlRepository.delete(url);
    }
}
