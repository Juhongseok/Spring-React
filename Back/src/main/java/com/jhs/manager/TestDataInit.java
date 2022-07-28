package com.jhs.manager;

import com.jhs.manager.domain.Member;
import com.jhs.manager.domain.Team;
import com.jhs.manager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final TeamRepository teamRepository;

    @PostConstruct
    public void init(){
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();

        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .id("member"+i+"@naver.com")
                    .name("member"+i)
                    .password("member"+i)
                    .age(20+i)
                    .salary(1000000)
                    .build();
            member.setTeam(i%2 == 0 ? teamA : teamB);
        }

        teamRepository.save(teamA);
        teamRepository.save(teamB);
    }
}
