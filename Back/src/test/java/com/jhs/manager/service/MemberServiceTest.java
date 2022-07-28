package com.jhs.manager.service;

import com.jhs.manager.domain.Member;
import com.jhs.manager.domain.Team;
import com.jhs.manager.repository.MemberRepository;
import com.jhs.manager.repository.TeamRepository;
import com.jhs.manager.service.request.LoginMemberRequest;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
import com.jhs.manager.service.response.LoginMemberResponse;
import com.jhs.manager.service.response.MemberInfoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    MemberService memberService;
    
    @Test
    void saveMember(){
        //given
        SaveMemberRequest request = new SaveMemberRequest("id", "id", "id", 10);
        given(memberRepository.save(any(Member.class))).willReturn(Member.builder().build());
        //when
        String returnValue = memberService.saveMember(request);

        //then
        assertThat(returnValue).isEqualTo("ok");
    }
    
    @Test
    void login_user(){
        //given
        Member member = Member.builder()
                .id("userA")
                .name("userA")
                .password("userA")
                .age(10)
                .salary(1000)
                .build();
        LoginMemberRequest request = new LoginMemberRequest(member.getId(), member.getPassword());
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        //when
        LoginMemberResponse login = memberService.login(request);

        //then
        assertThat(login.getName()).isEqualTo(member.getName());
        assertThat(login.getRole()).isEqualTo("user");
    }

    @Test
    void login_error(){
        //given
        Member member = Member.builder()
                .id("userA")
                .name("userA")
                .password("userA")
                .age(10)
                .salary(1000)
                .build();
        LoginMemberRequest request = new LoginMemberRequest(member.getId(), member.getPassword());
        given(memberRepository.findById(any())).willThrow(new IllegalArgumentException("유저 정보 없음"));

        //when
        //then
        assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void login_admin(){
        //given
        LoginMemberRequest request = new LoginMemberRequest("admin", "1234");

        //when
        LoginMemberResponse login = memberService.login(request);

        //then
        assertThat(login.getName()).isEqualTo("admin");
        assertThat(login.getRole()).isEqualTo("ADMIN");
    }

    @Test
    void getMemberInfo(){
        //given
        Member member = Member.builder()
                .id("userA")
                .name("userA")
                .password("userA")
                .age(10)
                .salary(1000)
                .team(Team.builder().name("TeamA").build())
                .build();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        //when
        MemberInfoResponse memberInfo = memberService.getMemberInfo(member.getId());

        //then
        assertThat(memberInfo.getMemberId()).isEqualTo(member.getId());
    }

    @Test
    void deleteMember(){
        //given
        Member member = Member.builder()
                .id("userA")
                .name("userA")
                .password("userA")
                .age(10)
                .salary(1000)
                .build();

        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        willDoNothing().given(memberRepository).delete(member);

        //when
        memberService.deleteMember("userA");

        //then
        verify(memberRepository,times(1)).delete(member);
    }

    @Test
    void updateMember(){
        //given
        Member member = Member.builder()
                .id("userA")
                .name("userA")
                .password("userA")
                .age(10)
                .salary(1000)
                .team(new Team(1L, "teamA"))
                .build();

        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));
        given(teamRepository.findById(any())).willReturn(Optional.of(new Team(1L, "teamA")));

        //when
        memberService.updateMember(new UpdateMemberRequest(member.getId(), member.getSalary(), 2L));

        //then
        verify(memberRepository, times(1)).findById(member.getId());
        verify(teamRepository, times(1)).findById(2L);
    }

    @Test
    void getMembers(){
        //given
        List<Member> list = new ArrayList<>();
        given(memberRepository.findAll()).willReturn(list);

        //when
        List<MemberInfoResponse> members = memberService.getMembers();

        //then
        assertThat(members.size()).isEqualTo(list.size());
    }

}