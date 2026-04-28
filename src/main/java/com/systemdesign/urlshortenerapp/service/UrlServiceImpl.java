package com.systemdesign.urlshortenerapp.service;

import com.google.common.hash.Hashing;
import com.systemdesign.urlshortenerapp.model.Url;
import com.systemdesign.urlshortenerapp.model.UrlDto;
import com.systemdesign.urlshortenerapp.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService{

    @Autowired
   private UrlRepository urlRepository;

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
    public Url getEncodedUrl(String url) {
        Url urlToRet=urlRepository.findByShortLink(url);
        return urlToRet;

    }

    @Override
    public void deleteShortUrl(Url url) {
       urlRepository.delete(url);
    }
}
