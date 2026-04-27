package com.systemdesign.urlshortenerapp.repository;

import com.systemdesign.urlshortenerapp.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
    public Url findByShortLink(String shortLink);
}
