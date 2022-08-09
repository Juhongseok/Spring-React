package com.jhs.manager.controller;

import com.jhs.manager.controller.response.common.ListResponseData;
import com.jhs.manager.controller.response.common.SingleResponseData;
import com.jhs.manager.document.support.RestDocsTestSupport;
import com.jhs.manager.service.MemberService;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
import com.jhs.manager.service.response.MemberInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.jhs.manager.document.config.RestDocsConfig.field;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends RestDocsTestSupport {

    @MockBean
    protected MemberService memberService;

    @Test
    @DisplayName("getMemberInfo - Get /member?memberId")
    void common() throws Exception {
        //given
        MemberInfoResponse response = MemberInfoResponse.builder()
                .memberId("memberA@naver.com")
                .memberName("memberA")
                .password("memberA")
                .age(10)
                .salary(100)
                .build();
        given(memberService.getMemberInfo("memberA@naver.com")).willReturn(response);

        String memberId = "memberA@naver.com";
        //when
        ResultActions action = mockMvc.perform(get("/member?memberId=" + memberId));

        //then
        SingleResponseData responseData = SingleResponseData.of(response);
        action.andExpect(status().isOk())
                .andExpect(content().json(createJson(responseData)))
                .andExpect(jsonPath("$.data.memberId").exists())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("memberId").description("memberId to find")
                        ),
                        responseFields(
                                subsectionWithPath("data").description("DATA"),
                                fieldWithPath("statusCode").description("STATUS_CODE"),
                                fieldWithPath("message").description("MESSAGE")
                        )));

    }

    @Test
    @DisplayName("add - Post /member")
    void sign_up() throws Exception {
        ConstraintDescriptions saveMemberRequestConstraints = new ConstraintDescriptions(SaveMemberRequest.class);
        //given
        SaveMemberRequest request = new SaveMemberRequest("memberA", "memberA", "memberA!", 10);

        given(memberService.saveMember(request)).willReturn("ok");

        //when
        ResultActions action = mockMvc.perform(post("/member")
                .content(createJson(request))
                .contentType(APPLICATION_JSON));

        SingleResponseData responseData = SingleResponseData.of(memberService.saveMember(request));
        //then
        action.andExpect(status().isOk())
                .andExpect(content().json(createJson(responseData)))
                .andExpect(jsonPath("$.data", is("ok")))
                .andDo(restDocs.document(
                        requestFields(
                                attributes(key("title").value("Fields for User Create")),
                                fieldWithPath("id").description("user's id")
                                        .attributes(field("constraints", saveMemberRequestConstraints.descriptionsForProperty("id").toString())),
                                fieldWithPath("name").description("user's name")
                                        .attributes(field("constraints", saveMemberRequestConstraints.descriptionsForProperty("name").toString())),
                                fieldWithPath("password").description("user's password")
                                        .attributes(field("constraints", saveMemberRequestConstraints.descriptionsForProperty("password").toString())),
                                fieldWithPath("age").description("user's age")
                                        .attributes(field("constraints", saveMemberRequestConstraints.descriptionsForProperty("age").toString()))
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("statusCode").description("STATUS_CODE"),
                                fieldWithPath("message").description("MESSAGE"),
                                fieldWithPath("data").description("RESULT_DATA")
                        )));
    }

    @Test
    @DisplayName("getMemberInfo - Get /member?memberId")
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

        String memberId = "memberA@naver.com";
        //when
        ResultActions action = mockMvc.perform(get("/member?memberId=" + memberId));

        //then
        SingleResponseData responseData = SingleResponseData.of(response);
        action.andExpect(status().isOk())
                .andExpect(content().json(createJson(responseData)))
                .andExpect(jsonPath("$.data.memberId").exists())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("memberId").description("memberId to find")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),

                                fieldWithPath("memberId").type(JsonFieldType.STRING).description("memberId"),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("salary").type(JsonFieldType.NUMBER).description("salary")
                        )));
    }

    @Test
    @DisplayName("getMemberList - GET /members")
    void getMemberList() throws Exception {
        //given
        MemberInfoResponse response1 = MemberInfoResponse.builder()
                .memberId("memberA@naver.com")
                .memberName("memberA")
                .password("memberA")
                .age(10)
                .salary(100)
                .build();
        MemberInfoResponse response2 = MemberInfoResponse.builder()
                .memberId("memberA@naver.com")
                .memberName("memberA")
                .password("memberA")
                .age(10)
                .salary(100)
                .build();
        List<MemberInfoResponse> list = List.of(response1, response2);
        given(memberService.getMembers()).willReturn(list);

        //when
        ResultActions action = mockMvc.perform(get("/members"));

        //then
        ListResponseData responseData = ListResponseData.of(list);
        action.andExpect(status().isOk())
                .andExpect(content().json(createJson(responseData)))
                .andExpect(jsonPath("$.data[0].memberId").exists())
                .andDo(restDocs.document(
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("memberId").type(JsonFieldType.STRING).description("memberId"),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("salary").type(JsonFieldType.NUMBER).description("salary")
                        )));
    }

    @Test
    @DisplayName("deleteMember - Delete /member/{memberId}")
    void deleteMember() throws Exception {
        //given
        String pathVariable = "memberA@naver.com";
        willDoNothing().given(memberService).deleteMember(pathVariable);

        //when
        ResultActions action = mockMvc.perform(RestDocumentationRequestBuilders.delete("/member/{memberId}", pathVariable));
        SingleResponseData responseData = SingleResponseData.of("ok");

        //then
        action.andExpect(status().isOk())
                .andExpect(content().json(createJson(responseData)))
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("memberId").description("memberId")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").description("STATUS_CODE"),
                                fieldWithPath("message").description("MESSAGE"),
                                fieldWithPath("data").description("RESPONSE_DATA")
                        )));
    }

    @Test
    @DisplayName("updateMember - Patch /member/{memberId}")
    void updateMember() throws Exception {
        //given
        String memberId = "member1@naver.com";

        UpdateMemberRequest request = new UpdateMemberRequest(memberId, 1000000);

        willDoNothing().given(memberService).updateMember(request);

        //when
        ResultActions action = mockMvc.perform(RestDocumentationRequestBuilders.patch("/member/{memberId}", memberId)
                .contentType(APPLICATION_JSON)
                .content(createJson(request)));

        SingleResponseData responseData = SingleResponseData.of("ok");

        //then
        action.andExpect(status().isOk())
                .andExpect(content().json(createJson(responseData)))
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("memberId").description("memberId")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").description("STATUS_CODE"),
                                fieldWithPath("message").description("MESSAGE"),
                                fieldWithPath("data").description("RESPONSE_DATA")
                        )));
    }

    @Test
    @DisplayName("Error")
    void error() throws Exception {
        //given
        SaveMemberRequest request = new SaveMemberRequest("", "", "!", 0);

        //when
        ResultActions action = mockMvc.perform(post("/member")
                .content(createJson(request))
                .contentType(APPLICATION_JSON));

        //then
        action.andExpect(status().isBadRequest())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("statusCode").description("STATUS_CODE"),
                                fieldWithPath("message").description("MESSAGE"),
                                fieldWithPath("error").description("RESPONSE_DATA"),
                                fieldWithPath("error[].field").description("Field Validation Failed"),
                                fieldWithPath("error[].value").description("Input Value"),
                                fieldWithPath("error[].message").description("Validation Fail Message")
                        )));
    }
}