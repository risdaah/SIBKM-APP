package com.serverapp.serverapp.repository;

import com.serverapp.serverapp.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findByName(String name);
}