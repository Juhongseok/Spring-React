package com.jhs.manager.service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateMemberForm {
    private String memberId;
    private int salary;
    private List<TeamInfo> teamInfo;


}
