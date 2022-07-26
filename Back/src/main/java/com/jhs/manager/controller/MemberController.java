package com.jhs.manager.controller;

import com.jhs.manager.controller.response.common.ListResponseData;
import com.jhs.manager.controller.response.common.ResponseData;
import com.jhs.manager.controller.response.common.SingleResponseData;
import com.jhs.manager.service.MemberService;
import com.jhs.manager.service.request.SaveMemberRequest;
import com.jhs.manager.service.request.UpdateMemberRequest;
import com.jhs.manager.service.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 멤버 추가
     *
     * @param request
     * @return
     */
    @PostMapping("/member")
    public ResponseData addMember(@Validated @RequestBody SaveMemberRequest request) {
        return SingleResponseData.of(memberService.saveMember(request));
    }

    /**
     * 멤버리스트 조회
     *
     * @return
     */
    @GetMapping("/members")
    public ResponseData getMemberList(){
        return ListResponseData.of(memberService.getMembers());
    }

    /**
     * 멤버 한명 정보 조회
     * @param memberId
     * @return
     */
    @GetMapping("/member")
    public ResponseData getMemberInfo(@RequestParam("memberId") String memberId){
        return SingleResponseData.of(memberService.getMemberInfo(memberId));
    }

    /**
     * 멤버 삭제
     *
     * @param memberId
     * @return
     */
    @DeleteMapping("/member/{memberId}")
    public ResponseData deleteMember(@PathVariable("memberId") String memberId){
        memberService.deleteMember(memberId);
        return SingleResponseData.of("ok");
    }

    /**
     * 멤버 업데이트
     * @param memberId
     * @param request
     * @return
     */
    @PatchMapping("/member/{memberId}")
    public ResponseData updateMember(@PathVariable("memberId") String memberId, @Validated @RequestBody UpdateMemberRequest request){
        request.setId(memberId);
        memberService.updateMember(request);
        return SingleResponseData.of("ok");
    }

    @GetMapping("/test")
    public ResponseData test(){
        return SingleResponseData.of("ok");
    }
}
