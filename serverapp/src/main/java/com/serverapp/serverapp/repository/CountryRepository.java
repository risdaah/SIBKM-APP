package com.serverapp.serverapp.repository;

import com.serverapp.serverapp.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query("SELECT c FROM Country c WHERE c.name = :name AND c.region.name = :regionName")
    List<Country> findByNameOrRegionName(@Param("name") String name, @Param("regionName") String regionName);
}