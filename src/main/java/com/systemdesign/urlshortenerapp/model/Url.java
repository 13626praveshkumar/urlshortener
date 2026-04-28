package com.systemdesign.urlshortenerapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

@Entity
public class Url {

    @Id
    @GeneratedValue
    private long id;
    @Lob
    private String originalUrl;
    private String shortLink;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDateTime;

    public Url(long id, String originalUrl, String shortUrl, LocalDateTime createdDate, LocalDateTime expirationDateTime) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortLink = shortUrl;
        this.createdDate = createdDate;
        this.expirationDateTime = expirationDateTime;
    }

    public Url() {

    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }


    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(LocalDateTime expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", originalUrl='" + originalUrl + '\'' +
                ", shortUrl='" + shortLink + '\'' +
                ", createdDate=" + createdDate +
                ", expirationDateTime=" + expirationDateTime +
                '}';
    }
}
