package com.serverapp.serverapp.controller;

import com.serverapp.serverapp.entity.Region;
import com.serverapp.serverapp.service.RegionService;
import lombok.AllArgsConstructor;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/region")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RegionController {

    private RegionService regionService;

    // Get all regions
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public List<Region> fetchAllRegions() {
        return regionService.fetchAllRegions();
    }

    // Get region by ID
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public Region findRegionById(@PathVariable Integer id) {
        return regionService.findRegionById(id);
    }

    // Create new region
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public Region createRegion(@RequestBody Region region) {
        return regionService.createRegion(region);
    }

    // Update existing region
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public Region updateRegion(@PathVariable Integer id, @RequestBody Region region) {
        return regionService.updateRegion(id, region);
    }

    // Delete existing region
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public Region deleteRegion(@PathVariable Integer id) {
        return regionService.deleteRegion(id);
    }
}