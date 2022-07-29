package com.jhs.manager.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberInfoResponse {
    private String memberId;
    private String memberName;
    private String password;
    private int age;
    private int salary;
}
