package com.jhs.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhs.manager.service.MemberService;
import com.jhs.manager.service.request.LoginMemberRequest;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
import com.jhs.manager.service.response.LoginMemberResponse;
import com.jhs.manager.service.response.MemberInfoResponse;
import com.jhs.manager.service.response.UpdateMemberForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mock;
    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("add - Post /member")
    void signUp() throws Exception {
        //given
        SaveMemberRequest request = new SaveMemberRequest("memberA", "memberA", "memberA!", 10);

        given(memberService.saveMember(request)).willReturn("ok");
        String data = objectMapper.writeValueAsString(request);

        //when
        ResultActions action = mock.perform(post("/member")
                .content(data)
                .contentType(APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    @DisplayName("getMemberInfo - Get /member/{memberId}")
    void getMemberInfo() throws Exception {
        //given
        MemberInfoResponse response = MemberInfoResponse.builder()
                .memberId("memberA@naver.com")
                .memberName("memberA")
                .password("memberA")
                .age(10)
                .salary(100)
                .build();
        given(memberService.getMemberInfo("memberA@naver.com")).willReturn(response);

        String pathVariable = "memberA@naver.com";
        //when
        ResultActions action = mock.perform(get("/member/{memberId}", pathVariable));

        //then
        action.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.memberId").exists());
    }

    @Test
    @DisplayName("deleteMember - Delete /member/{memberId}")
    void deleteMember() throws Exception {
        //given
        String pathVariable = "memberA@naver.com";
        willDoNothing().given(memberService).deleteMember(pathVariable);

        //when
        ResultActions action = mock.perform(delete("/member/{memberId}", pathVariable));

        //then
        action.andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    @DisplayName("deleteMember error - Delete /member/{memberId}")
    void deleteMember_error() throws Exception {
        //given
        String pathVariable = "asdf";
        willThrow(new IllegalArgumentException("유저 정보 없음"))
                .willDoNothing()
                .given(memberService).deleteMember(pathVariable);

        //when
        ResultActions action = mock.perform(delete("/member/{memberId}", pathVariable));

        //then
        action.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("유저 정보 없음")));
    }

    @Test
    @DisplayName("updateMemberForm - Get /member/{memberId}/update")
    void updateMemberForm() throws Exception {
        //given
        String memberId = "member1@naver.com";
        UpdateMemberForm updateMemberForm = new UpdateMemberForm(memberId, 1000);
        given(memberService.getUpdateMemberInfo(memberId)).willReturn(updateMemberForm);

        //when
        ResultActions action = mock.perform(get("/member/{memberId}/update", memberId));

        //then
        action.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updateMemberForm)))
                .andExpect(jsonPath("$.memberId", is(memberId)));
    }

    @Test
    @DisplayName("updateMemberForm_error - Get /member/{memberId}/update")
    void updateMemberForm_error() throws Exception {
        //given
        String memberId = "asdf";
        given(memberService.getUpdateMemberInfo(memberId)).willThrow(new IllegalArgumentException("유저 정보 없음"));

        //when
        ResultActions action = mock.perform(get("/member/{memberId}/update", memberId));

        //then
        action.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("유저 정보 없음")));
    }

    @Test
    @DisplayName("updateMember - Patch /member/{memberId}")
    void updateMember() throws Exception {
        //given
        String memberId = "member1@naver.com";

        UpdateMemberRequest request = new UpdateMemberRequest(memberId, 1000000);
        String content = objectMapper.writeValueAsString(request);

        willDoNothing().given(memberService).updateMember(request);

        //when
        ResultActions action = mock.perform(patch("/member/{memberId}", memberId)
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        action.andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

}