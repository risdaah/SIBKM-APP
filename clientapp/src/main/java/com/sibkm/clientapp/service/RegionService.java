package com.sibkm.clientapp.service;

import com.sibkm.clientapp.entity.Region;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RegionService {

        private String BASE_URL = "http://localhost:9000/region";

        @Autowired
        private RestTemplate restTemplate;

        // Mendapatkan semua region
        public List<Region> getAll() {
                return restTemplate
                                .exchange(
                                                BASE_URL,
                                                HttpMethod.GET,
                                                null,
                                                new ParameterizedTypeReference<List<Region>>() {
                                                })
                                .getBody();
        }

        // Mendapatkan region berdasarkan ID
        public Region getById(Integer id) {
                String endpoint = BASE_URL + "/" + id;
                log.info("endpoint serverapp = {}", endpoint);

                return restTemplate
                                .exchange(endpoint, HttpMethod.GET, null, Region.class)
                                .getBody();
        }

        // Menambahkan region baru
        public Region createRegion(Region region) {
                return restTemplate
                                .exchange(
                                                BASE_URL,
                                                HttpMethod.POST,
                                                new HttpEntity<Region>(region),
                                                new ParameterizedTypeReference<Region>() {
                                                })
                                .getBody();
        }

        // Mengupdate Region
        public Region updateRegion(Integer id, Region region) {
                HttpEntity<Region> request = new HttpEntity<>(region);
                String endpoint = BASE_URL + "/" + id;
                return restTemplate
                                .exchange(endpoint, HttpMethod.PUT, request, Region.class)
                                .getBody();
        }

        // Menghapus Region
        public Region deleteRegion(Integer id) {
                return restTemplate
                                .exchange(BASE_URL.concat("/" + id), HttpMethod.DELETE, null, Region.class)
                                .getBody();
        }

}