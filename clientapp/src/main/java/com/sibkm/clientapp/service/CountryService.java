package com.sibkm.clientapp.service;

import com.sibkm.clientapp.entity.Country;

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
public class CountryService {

        private String BASE_URL = "http://localhost:9000/country";

        @Autowired
        private RestTemplate restTemplate;

        // Mendapatkan semua Country
        public List<Country> getAll() {
                return restTemplate
                                .exchange(
                                                BASE_URL,
                                                HttpMethod.GET,
                                                null,
                                                new ParameterizedTypeReference<List<Country>>() {
                                                })
                                .getBody();
        }

        // Mendapatkan Country berdasarkan ID
        public Country getById(Integer id) {
                String endpoint = BASE_URL + "/" + id;
                log.info("endpoint serverapp = {}", endpoint);

                return restTemplate
                                .exchange(endpoint, HttpMethod.GET, null, Country.class)
                                .getBody();
        }

        // Menambahkan Country baru
        public Country createCountry(Country country) {
                return restTemplate
                                .exchange(
                                                BASE_URL,
                                                HttpMethod.POST,
                                                new HttpEntity<>(country),
                                                new ParameterizedTypeReference<Country>() {
                                                })
                                .getBody();
        }

        // Mengupdate Country
        public Country updateCountry(Integer id, Country country) {
                HttpEntity<Country> request = new HttpEntity<>(country);
                String endpoint = BASE_URL + "/" + id;
                return restTemplate
                                .exchange(endpoint, HttpMethod.PUT, request, Country.class)
                                .getBody();
        }

        // Menghapus Country
        public Country deleteCountry(Integer id) {
                return restTemplate
                                .exchange(BASE_URL.concat("/" + id), HttpMethod.DELETE, null, Country.class)
                                .getBody();
        }
}
