package com.msa.example.order.repository;

import com.msa.example.order.domain.StoreOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreOrderRepository extends JpaRepository<StoreOrderEntity, UUID> {
}
