package com.serverapp.serverapp.service;

import com.serverapp.serverapp.entity.Region;
import com.serverapp.serverapp.repository.RegionRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    // Get all regions
    public List<Region> fetchAllRegions() {
        return regionRepository.findAll();
    }

    // Find region by ID
    public Region findRegionById(Integer id) {
        return regionRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));
    }

    // Create new region
    public Region createRegion(Region region) {
        return regionRepository.save(region);
    }

    // Update existing region
    public Region updateRegion(Integer id, Region region) {
        findRegionById(id);
        region.setId(id);
        return regionRepository.save(region);
    }

    // Delete existing region
    public Region deleteRegion(Integer id) {
        Region region = findRegionById(id);
        regionRepository.delete(region);
        return region;
    }
}