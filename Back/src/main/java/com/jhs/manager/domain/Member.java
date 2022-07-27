package com.jhs.manager.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Member implements Persistable<String> {

    @Id
    private String id;
    private String name;
    private String password;
    private int age;
    private int salary;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @Builder
    public Member(String id, String name, String password, int age, int salary, Team team) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.salary = salary;
        this.team = team;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public void changeSalary(int salary){
        this.salary = salary;
    }

    public void setTeam(Team team){
        if (this.team == null) {
            team.getMembers().add(this);
        }
        this.team = team;
    }
}
