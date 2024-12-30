package com.serverapp.serverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Integer id;
    private String password;
    private String username;
    private Integer employeeId;
}
