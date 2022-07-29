package com.jhs.manager.service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberForm {
    private String memberId;
    private int salary;
}
