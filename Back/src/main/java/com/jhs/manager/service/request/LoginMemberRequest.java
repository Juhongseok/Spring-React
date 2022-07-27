package com.jhs.manager.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberRequest {
    @NotBlank(message = "{NotBlank.id}")
    private String id;
    @NotBlank(message = "{NotBlank.password}")
    private String password;
}
