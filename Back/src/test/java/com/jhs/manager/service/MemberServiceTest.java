package com.jhs.manager.service;

import com.jhs.manager.domain.Member;
import com.jhs.manager.repository.MemberRepository;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
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
    void getMemberInfo(){
        //given
        Member member = Member.builder()
                .id("userA")
                .name("userA")
                .password("userA")
                .age(10)
                .salary(1000)
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
                .build();

        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        //when
        memberService.updateMember(new UpdateMemberRequest(member.getId(), member.getSalary()));

        //then
        verify(memberRepository, times(1)).findById(member.getId());
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