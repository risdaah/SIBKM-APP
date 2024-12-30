// package com.serverapp.serverapp;

package com.serverapp.serverapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse {

    private Integer id;
    private String code;
    private String name;
    private Integer region_id;
}