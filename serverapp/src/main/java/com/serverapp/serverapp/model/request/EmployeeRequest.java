package com.serverapp.serverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    private Integer id;
    private String email;
    private String name;
    private String phone;

}
