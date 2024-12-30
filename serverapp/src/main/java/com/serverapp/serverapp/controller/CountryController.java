package com.serverapp.serverapp.controller;

import com.serverapp.serverapp.entity.Country;
import com.serverapp.serverapp.model.request.CountryRequest;
import com.serverapp.serverapp.model.response.CountryResponse;
import com.serverapp.serverapp.service.CountryService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/country")
@PreAuthorize("hasRole('ADMIN','USER')")
public class CountryController {

    private CountryService countryService;

    // read all
    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    public List<Country> getAllCountries() {
        return countryService.fetchAllCountries();
    }

    // read by id
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    public Country getCountryById(@PathVariable Integer id) {
        return countryService.findCountryById(id);
    }

    // read by id with special response
    @GetMapping("/response/{id}")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    public CountryResponse getCountryResponse(@PathVariable Integer id) {
        return countryService.fetchCountryResponse(id);
    }

    // read by id with mapping output
    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    public Map<String, Object> getCountryDetails(@PathVariable Integer id) {
        return countryService.fetchCountryDetailsAsMap(id);
    }

    // create normally
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public Country createCountry(@RequestBody Country country) {
        return countryService.addNewCountry(country);
    }

    // create using manual dto
    @PostMapping("/manual-dto")
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public Country createCountryWithManualDTO(@RequestBody CountryRequest countryRequest) {
        return countryService.addCountryWithManualDTO(countryRequest);
    }

    // create using automatic dto
    @PostMapping("/automatic-dto")
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public Country createCountryWithAutomaticDTO(@RequestBody CountryRequest countryRequest) {
        return countryService.addCountryWithAutomaticDTO(countryRequest);
    }

    // update dto
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public Country updateCountry(
            @PathVariable Integer id,
            @RequestBody Country country) {
        return countryService.updateCountry(id, country);
    }

    // delete dto
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public Country deleteCountry(@PathVariable Integer id) {
        return countryService.removeCountry(id);
    }
}