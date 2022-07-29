package com.jhs.manager.service;

import com.jhs.manager.controller.response.error.ErrorCode;
import com.jhs.manager.domain.Member;
import com.jhs.manager.repository.MemberRepository;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
import com.jhs.manager.service.response.MemberInfoResponse;
import com.jhs.manager.service.response.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String saveMember(SaveMemberRequest request) {
        Member newMember = Member.builder()
                .id(request.getId())
                .name(request.getName())
                .password(request.getPassword())
                .age(request.getAge())
                .salary(1_000_000)
                .build();
        memberRepository.save(newMember);
        return "ok";
    }

    public void updateMember(UpdateMemberRequest request) {
        Member findMember = findMember(request.getId());
        findMember.changeSalary(request.getSalary());
    }

    public void deleteMember(String id) {
        Member findMember = findMember(id);
        memberRepository.delete(findMember);
    }

    public MemberInfoResponse getMemberInfo(String id) {
        Member findMember = findMember(id);
        return MemberInfoResponse.builder()
                .memberId(findMember.getId())
                .memberName(findMember.getName())
                .age(findMember.getAge())
                .password(findMember.getPassword())
                .salary(findMember.getSalary())
                .build();
    }

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(m -> MemberInfoResponse.builder()
                        .memberId(m.getId())
                        .memberName(m.getName())
                        .age(m.getAge())
                        .password(m.getPassword())
                        .salary(m.getSalary())
                        .build())
                .collect(Collectors.<MemberInfoResponse>toList());
    }

    private Member findMember(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
