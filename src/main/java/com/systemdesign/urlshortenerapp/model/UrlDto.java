package com.systemdesign.urlshortenerapp.model;

public class UrlDto {

    private String url;
    private String expiryDateTime; //optional

    public UrlDto(String url, String expiryDateTime) {
        this.url = url;
        this.expiryDateTime = expiryDateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setShortUrl(String url) {
        this.url = url;
    }

    public String getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(String expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "Url='" + url + '\'' +
                ", expiryDateTime='" + expiryDateTime + '\'' +
                '}';
    }
}
