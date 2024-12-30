package com.serverapp.serverapp.service;

import com.serverapp.serverapp.entity.Country;
import com.serverapp.serverapp.entity.Region;
import com.serverapp.serverapp.model.request.CountryRequest;
import com.serverapp.serverapp.model.response.CountryResponse;
import com.serverapp.serverapp.repository.CountryRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final RegionService regionService;

    // get all
    public List<Country> fetchAllCountries() {
        return countryRepository.findAll();
    }

    // get by id
    public Country findCountryById(Integer id) {
        return countryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Country not found!"));
    }

    // get by id with custom response
    public CountryResponse fetchCountryResponse(Integer id) {
        Country country = findCountryById(id);
        CountryResponse response = new CountryResponse();

        response.setId(country.getId());
        response.setCode(country.getCode());
        response.setName(country.getName());
        response.setRegion_id(country.getRegion().getId());
        return response;
    }

    // get by id with mapping response
    public Map<String, Object> fetchCountryDetailsAsMap(Integer id) {
        Country country = findCountryById(id);
        Map<String, Object> details = new HashMap<>();

        details.put("country_id", country.getId());
        details.put("country_code", country.getCode());
        details.put("country_name", country.getName());
        details.put("region_id", country.getRegion().getId());
        details.put("region_name", country.getRegion().getName());

        return details;
    }

    // create new country normal
    public Country addNewCountry(Country country) {
        if (country.getRegion() != null && country.getRegion().getId() != null) {
            Region region = regionService.findRegionById(country.getRegion().getId());
            country.setRegion(region);
        }

        validateCountry(country);

        return countryRepository.save(country);
    }

    // Create new country with manual DTO
    public Country addCountryWithManualDTO(CountryRequest request) {
        validateCountryByRequest(request);

        Country country = new Country();
        country.setCode(request.getCode());
        country.setName(request.getName());

        Region region = regionService.findRegionById(request.getRegion_id());
        country.setRegion(region);

        return countryRepository.save(country);
    }

    // create new country with automatic DTO
    public Country addCountryWithAutomaticDTO(CountryRequest request) {
        Country country = new Country();
        BeanUtils.copyProperties(request, country);

        Region region = regionService.findRegionById(request.getRegion_id());
        country.setRegion(region);

        return countryRepository.save(country);
    }

    // Validation to make sure region name not gonna same with country name
    public void validateCountry(Country country) {
        // Make sure region not null
        if (country.getRegion() == null || country.getRegion().getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region must be specified for the country.");
        }

        // Search for an existing country with the same name and the same region
        List<Country> existingCountries = countryRepository.findByNameOrRegionName(
                country.getName(),
                country.getRegion().getName());

        // If there are countries with the same name in the same region, throw an
        // exception
        if (!existingCountries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country name already exists in the same region!");
        }

        // Added validation to ensure country name is not the same as region name
        // region
        if (country.getName().equalsIgnoreCase(country.getRegion().getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country name cannot be the same as region name!");
        }
    }

    // validation for request create
    public void validateCountryByRequest(CountryRequest request) {
        // get region name based on id
        String regionName = regionService.findRegionById(request.getRegion_id()).getName();

        // Check if there are countries with the same name in the same region
        List<Country> existingCountries = countryRepository.findByNameOrRegionName(request.getName(), regionName);
        if (!existingCountries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country name already exists in the same region!");
        }

        // Check if the country name is the same as the region name
        if (request.getName().equalsIgnoreCase(regionName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country name cannot be the same as region name!");
        }
    }

    // put/update country
    public Country updateCountry(Integer id, Country countryUpdates) {
        Country existingCountry = findCountryById(id);

        // Update only the fields that are not null
        if (countryUpdates.getCode() != null) {
            existingCountry.setCode(countryUpdates.getCode());
        }
        if (countryUpdates.getName() != null) {
            existingCountry.setName(countryUpdates.getName());
        }
        if (countryUpdates.getRegion() != null && countryUpdates.getRegion().getId() != null) {
            Region region = regionService.findRegionById(countryUpdates.getRegion().getId());
            existingCountry.setRegion(region);
        }

        return countryRepository.save(existingCountry);
    }

    // delete country
    public Country removeCountry(Integer id) {
        Country country = findCountryById(id);
        countryRepository.delete(country);
        return country;
    }
}