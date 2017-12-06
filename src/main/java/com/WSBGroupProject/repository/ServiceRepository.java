package com.WSBGroupProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WSBGroupProject.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long>  {
    List<Service> findAll();
    Optional<Service> findById(Long id);
}
