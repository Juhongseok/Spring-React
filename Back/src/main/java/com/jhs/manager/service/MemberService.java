package com.jhs.manager.service;

import com.jhs.manager.service.response.TeamInfo;
import com.jhs.manager.service.response.UpdateMemberForm;
import com.jhs.manager.domain.Member;
import com.jhs.manager.domain.Team;
import com.jhs.manager.repository.MemberRepository;
import com.jhs.manager.repository.TeamRepository;
import com.jhs.manager.service.request.LoginMemberRequest;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
import com.jhs.manager.service.response.LoginMemberResponse;
import com.jhs.manager.service.response.MemberInfoResponse;
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
    private final TeamRepository teamRepository;

    public String saveMember(SaveMemberRequest request) {
        Member newMember = Member.builder()
                .id(request.getId())
                .name(request.getName())
                .password(request.getPassword())
                .age(request.getAge())
                .salary(1_000_000)
                .team(Team.builder()
                        .name("No Team")
                        .build())
                .build();
        memberRepository.save(newMember);
        return "ok";
    }

    @Transactional(readOnly = true)
    public LoginMemberResponse login(LoginMemberRequest request) {
        if (request.getId().equals("admin")) {
            return getLoginMemberResponse(request.getPassword(), "1234", "admin", "ADMIN");
        }

        Member loginMember = findMember(request.getId());
        return getLoginMemberResponse(loginMember.getPassword(), request.getPassword(), loginMember.getName(), "user");
    }

    public UpdateMemberForm getUpdateMemberInfo(String memberId) {
        Member member = findMember(memberId);

        List<TeamInfo> teamInfoList = teamRepository.findAll().stream()
                .map(team -> new TeamInfo(team.getId(), team.getName()))
                .collect(Collectors.toList());
        return new UpdateMemberForm(member.getId(), member.getSalary(), teamInfoList);
    }

    public void updateMember(UpdateMemberRequest request) {
        Member findMember = findMember(request.getId());
        findMember.changeSalary(request.getSalary());

        Team team = teamRepository.findById(request.getTeamId()).get();
        findMember.setTeam(team);
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
                .teamName(findMember.getTeam().getName())
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
                        .teamName(m.getTeam().getName())
                        .build())
                .collect(Collectors.<MemberInfoResponse>toList());
    }

    private LoginMemberResponse getLoginMemberResponse(String password, String findPassword, String name, String role) {
        if (password.equals(findPassword)) {
            return new LoginMemberResponse(name, role);
        }
        throw new IllegalArgumentException("비밀번호 오류");
    }

    private Member findMember(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 없음"));
    }
}
