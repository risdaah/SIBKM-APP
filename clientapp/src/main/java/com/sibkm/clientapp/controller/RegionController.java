package com.sibkm.clientapp.controller;

import com.sibkm.clientapp.entity.Region;
import com.sibkm.clientapp.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/region")
public class RegionController {

        private RegionService regionService;

        // get all region
        @GetMapping
        public String getAll(Model model) {
                model.addAttribute("regions", regionService.getAll());
                model.addAttribute("isActive", "region");
                return "region/HomeRegion";
        }

        // get region by id
        @GetMapping("/{id}")
        public String getById(@PathVariable Integer id, Model model) {
                model.addAttribute("region", regionService.getById(id));
                return "region/RegionDetail";
        }

        // call create region form
        @GetMapping("/create")
        public String createView(Region region, Model model) {
                model.addAttribute("region", new Region());
                return "region/CreateRegionForm";
        }

        // create region
        @PostMapping
        public String create(Region region) {
                regionService.createRegion(region);
                return "redirect:/region";
        }

        // call region update form
        @GetMapping("/update/{id}")
        public String refresh(
                        @PathVariable Integer id,
                        Region region,
                        Model model) {
                model.addAttribute("region", regionService.getById(id));
                return "region/UpdateRegionForm";
        }

        // update region
        @PutMapping("/{id}")
        public String update(@PathVariable Integer id, Region region) {
                regionService.updateRegion(id, region);
                return "redirect:/region";
        }

        // delete region
        @DeleteMapping("/{id}")
        public String delete(@PathVariable Integer id) {
                regionService.deleteRegion(id);
                return "redirect:/region";
        }
}