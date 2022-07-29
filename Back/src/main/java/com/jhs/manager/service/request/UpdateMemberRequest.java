package com.jhs.manager.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRequest {
    private String id;
    @NotNull(message = "{NotNull.UpdateMemberRequest.salary}")
    private Integer salary;
}
