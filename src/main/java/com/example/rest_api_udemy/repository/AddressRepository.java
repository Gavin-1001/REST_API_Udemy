package com.example.rest_api_udemy.repository;

import com.example.rest_api_udemy.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
