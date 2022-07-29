package com.jhs.manager;

import com.jhs.manager.domain.Member;
import com.jhs.manager.repository.MemberRepository;
import com.jhs.manager.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    @PostConstruct
    public void init(){
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .id("member"+i+"@naver.com")
                    .name("member"+i)
                    .password("member"+i)
                    .age(20+i)
                    .salary(1000000)
                    .build();
            memberRepository.save(member);
        }

    }
}
