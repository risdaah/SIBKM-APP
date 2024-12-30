package com.sibkm.clientapp.controller;

import com.sibkm.clientapp.entity.Country;
import com.sibkm.clientapp.entity.Region;
import com.sibkm.clientapp.service.CountryService;
import com.sibkm.clientapp.service.RegionService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/country")
public class CountryController {

    private CountryService countryService;
    private RegionService regionService;

    // get all country
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("countrys", countryService.getAll());
        model.addAttribute("isActive", "country");
        return "country/HomeCountry";
    }

    // get country by id
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        model.addAttribute("country", countryService.getById(id));
        return "country/CountryDetail";
    }

    // Call create country form
    @GetMapping("/create")
    public String createView(Model model) {
        model.addAttribute("country", new Country());
        model.addAttribute("regions", regionService.getAll()); // Ambil semua region
        return "country/CreateCountryForm"; // Ganti dengan path yang sesuai
    }

    // Create country
    @PostMapping
    public String create(@RequestParam String code, @RequestParam String name, @RequestParam Integer regionId) {
        Country country = new Country();
        country.setCode(code);
        country.setName(name);

        // call region based on regionId
        Region region = regionService.getById(regionId);
        country.setRegion(region); // Set region ke country

        countryService.createCountry(country); // Simpan country
        return "redirect:/country";
    }

    // Call country update form
    @GetMapping("/update/{id}")
    public String refresh(@PathVariable Integer id, Model model) {
        Country country = countryService.getById(id);
        if (country == null) {
            return "redirect:/country?error=Country not found";
        }
        model.addAttribute("country", country);
        model.addAttribute("regions", regionService.getAll());
        return "country/UpdateCountryForm";
    }

    // Update country
    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestParam String code, @RequestParam String name,
            @RequestParam Integer regionId) {
        Country country = new Country();
        country.setId(id);
        country.setCode(code);
        country.setName(name);

        // call region based on regionId
        Region region = regionService.getById(regionId);
        if (region == null) {
            return "redirect:/country/update/" + id + "?error=Region not found";
        }

        country.setRegion(region);
        countryService.updateCountry(id, country);

        return "redirect:/country";
    }

    // Delete Country
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        countryService.deleteCountry(id);
        return "redirect:/country";
    }
}
