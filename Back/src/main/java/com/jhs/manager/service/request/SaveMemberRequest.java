package com.jhs.manager.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveMemberRequest {
    @NotBlank(message = "{NotBlank.id}")
    private String id;
    @NotBlank(message = "{NotBlank.name}")
    private String name;
    @NotBlank(message = "{NotBlank.password}")
    private String password;
    @NotNull(message = "{NotNull.age}")
    private Integer age;
}
