package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team teamB = new Team();
            team.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId());
            // EAGER 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
            // Member를 가지고 왔더니 Team이 즉시로딩이 되어있음. 값이 다 나와 있어야 함.
            // Member쿼리 나가고 쿼리가 별도로 나가게 됨.
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            // SQL: select * from Member;
            // SQL: select % from Team where TEAM_ID = xxx

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
