package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private  Long id;
    @Column(name = "USERNAME")
    private String username;

    // team 프록시 객체를 조회, Member 만 조회함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
