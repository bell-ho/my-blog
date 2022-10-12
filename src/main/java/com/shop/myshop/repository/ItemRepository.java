package com.shop.myshop.repository;

import com.shop.myshop.domain.Item;
import com.shop.myshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
