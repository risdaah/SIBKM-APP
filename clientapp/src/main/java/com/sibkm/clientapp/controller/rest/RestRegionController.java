package com.sibkm.clientapp.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sibkm.clientapp.entity.Region;
import com.sibkm.clientapp.service.RegionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/region")
public class RestRegionController {

    private RegionService regionService;

    // get all region
    @GetMapping
    public List<Region> getAll() {
        return regionService.getAll();
    }

    // get region by id for detail
    @GetMapping("/{id}")
    public Region getById(@PathVariable Integer id) {
        return regionService.getById(id);
    }

    // create region
    @PostMapping
    public Region createRegion(@RequestBody Region region) {
        return regionService.createRegion(region);
    }

    // update region
    @PutMapping("/{id}")
    public Region updateRegion(@PathVariable Integer id, @RequestBody Region region) {
        return regionService.updateRegion(id, region);
    }

    // delete region
    @DeleteMapping("/{id}")
    public Region deleteRegion(@PathVariable Integer id) {
        return regionService.deleteRegion(id);
    }

}
