package com.smart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuditInfo {

    private String email;
    private String userId;
    private String role;
    private String companyId;
}