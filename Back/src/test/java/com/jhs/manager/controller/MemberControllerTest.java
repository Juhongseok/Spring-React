package com.jhs.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhs.manager.controller.response.common.SingleResponseData;
import com.jhs.manager.service.MemberService;
import com.jhs.manager.service.request.SaveMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    MockMvc mock;
    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("add - Post /member")
    void sign_up() throws Exception {
        ConstraintDescriptions saveMemberRequestConstraints = new ConstraintDescriptions(SaveMemberRequest.class);
        //given
        SaveMemberRequest request = new SaveMemberRequest("memberA", "memberA", "memberA!", 10);

        given(memberService.saveMember(request)).willReturn("ok");
        String data = objectMapper.writeValueAsString(request);

        //when
        ResultActions action = mock.perform(post("/member")
                .content(data)
                .contentType(APPLICATION_JSON));

        SingleResponseData responseData = SingleResponseData.of(memberService.saveMember(request));
        //then
        action.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseData)))
                .andExpect(jsonPath("$.data", is("ok")))
                .andDo(document("member/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                attributes(key("title").value("Fields for User Create")),
                                fieldWithPath("id").description("user's id")
                                        .attributes(key("constraints").value(saveMemberRequestConstraints.descriptionsForProperty("id"))),
                                fieldWithPath("name").description("user's name")
                                        .attributes(key("constraints").value(saveMemberRequestConstraints.descriptionsForProperty("name"))),
                                fieldWithPath("password").description("user's password")
                                        .attributes(key("constraints").value(saveMemberRequestConstraints.descriptionsForProperty("password"))),
                                fieldWithPath("age").description("user's age")
                                        .attributes(key("constraints").value(saveMemberRequestConstraints.descriptionsForProperty("age")))
                        ),
                        responseFields(
                                fieldWithPath("statusCode").description("STATUS_CODE"),
                                fieldWithPath("message").description("MESSAGE"),
                                fieldWithPath("data").description("RESPONSE_DATA")
                        )));
    }

   /* @Test
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
*/
}