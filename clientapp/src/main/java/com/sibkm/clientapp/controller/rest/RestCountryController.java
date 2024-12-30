package com.sibkm.clientapp.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sibkm.clientapp.entity.Country;
import com.sibkm.clientapp.service.CountryService;
import lombok.AllArgsConstructor;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/api/country")
public class RestCountryController {

    private CountryService countryService;

    // get all country
    @GetMapping
    public List<Country> getAll() {
        return countryService.getAll();
    }

    // get country by id for detail
    @GetMapping("/{id}")
    public Country getById(@PathVariable Integer id) {
        return countryService.getById(id);
    }

    // create country
    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    // update country
    @PutMapping("/{id}")
    public Country updateCountry(@PathVariable Integer id, @RequestBody Country country) {
        return countryService.updateCountry(id, country);
    }

    // delete country
    @DeleteMapping("/{id}")
    public Country deleteCountry(@PathVariable Integer id) {
        return countryService.deleteCountry(id);
    }

}
