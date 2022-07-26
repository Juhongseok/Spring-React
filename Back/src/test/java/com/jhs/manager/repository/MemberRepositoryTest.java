package com.jhs.manager.repository;

import com.jhs.manager.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    private Member member;
    @BeforeEach
    void before() {
        member = Member.builder()
                .id("memberA@naver.com")
                .name("memberA")
                .password("memberA!")
                .age(10)
                .salary(100)
                .build();
    }

    @Test
    void saveMember(){
        //given
        //when
        Member saveMember = memberRepository.save(member);

        //then
        assertThat(member.getName()).isEqualTo(saveMember.getName());
    }

    @Test
    void findMember(){
        //given
        memberRepository.save(member);
        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById("memberA@naver.com").get();

        //then
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    void updateMember(){
        //given
        Member saveMember = memberRepository.save(member);

        //when
        saveMember.changeSalary(1000);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findById("memberA@naver.com").get();

        //then
        assertThat(findMember.getSalary()).isEqualTo(saveMember.getSalary());
    }

    @Test
    void deleteMember(){
        //given
        Member saveMember = memberRepository.save(member);

        //when
        memberRepository.delete(saveMember);
        em.flush();
        em.clear();

        //then
        assertThat(memberRepository.findById("memberA@naver.com")).isEmpty();
    }

}