package com.shop.myshop.repository;

import com.shop.myshop.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images,Long> {
}
