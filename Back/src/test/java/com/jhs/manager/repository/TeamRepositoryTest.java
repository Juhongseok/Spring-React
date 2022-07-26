package com.jhs.manager.repository;

import com.jhs.manager.domain.Member;
import com.jhs.manager.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    private Team team;
    @BeforeEach
    void before() {
        team = Team.builder()
                .name("teamA")
                .build();
    }

    @Test
    void saveTeam(){
        //given
        //when
        Team saveTeam = teamRepository.save(team);

        //then
        assertThat(saveTeam.getName()).isEqualTo("teamA");
    }

    @Test
    void findTeam(){
        //given
        Team saveTeam = teamRepository.save(team);

        em.flush();
        em.clear();
        //when
        Team findTeam = teamRepository.findById(saveTeam.getId()).get();

        //then
        assertThat(findTeam.getName()).isEqualTo("teamA");
    }

    @Test
    void updateTeam(){
        //given
        Team saveTeam = teamRepository.save(team);

        //when
        saveTeam.changeTeamName("new TeamA");
        em.flush();
        em.clear();

        Team findTeam = teamRepository.findById(saveTeam.getId()).get();

        //then
        assertThat(findTeam.getName()).isEqualTo("new TeamA");
    }

    @Test
    void deleteTeam(){
        //given
        Team saveTeam = teamRepository.save(team);

        //when
        teamRepository.delete(saveTeam);
        em.flush();
        em.clear();

        //then
        assertThat(teamRepository.findById(saveTeam.getId())).isEmpty();
    }

    @Test
    void member_teamSave(){
        //given
        Member member = Member.builder()
                .id("memberA@naver.com")
                .name("memberA")
                .password("memberA!")
                .age(10)
                .salary(100)
                .build();
        member.setTeam(team);

        //when
        Team saveTeam = teamRepository.save(team);

        em.flush();
        em.clear();

        Team findTeam = teamRepository.findById(saveTeam.getId()).get();

        //then
        assertThat(findTeam.getMembers().size()).isEqualTo(1);
        assertThat(findTeam.getMembers().get(0).getName()).isEqualTo(member.getName());
    }
}