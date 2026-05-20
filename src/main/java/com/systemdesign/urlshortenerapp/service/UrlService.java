package com.systemdesign.urlshortenerapp.service;

import com.systemdesign.urlshortenerapp.model.Url;
import com.systemdesign.urlshortenerapp.model.UrlDto;

public interface UrlService {
    public Url generateShortUrl(UrlDto urlDto);
    public Url persistShortUrl(Url url);
    public String getEncodedUrl(String url);
    public void deleteShortUrl(Url url);

  }
