package com.WastageWreckerBackend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WastageWreckerBackend.Models.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long>{

    public List<Promotion> findAllByAddressAddressId(Long addressId);

    public List<Promotion> findAllByOwnerUserId(Long userId);

    
}
