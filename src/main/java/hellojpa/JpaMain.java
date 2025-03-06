package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member.getId());
            // Member m2 = em.find(Member.class, member2.getId());
            Member m3 = em.getReference(Member.class, member2.getId());

            // 당연히 타입비교 true
            // System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));

            // 하지만 false가 나옴 왜냐? 프록시 가짜 클래스니까
            System.out.println("m1 == m3: " + (m1.getClass() == m3.getClass()));

            // 타입비교 할 때는 instanceof 사용 해야 됨. (프록시 객체도 실제 엔티티를 상속하고 있기 때문에 true)
            System.out.println("instanceof = " + (m3 instanceof Member));


            // Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId()); // 데이터베이스에 쿼리 안함.
            System.out.println("findMember = " + findMember.getClass()); // 프록시 클래스 (가짜 클래스)

            System.out.println("findMember.id = " + findMember.getId());

            // 프록시에서 초기화 요청을 한 후에 DB를 조회하고, 실제 Entity를 생성한 후에 target에 저장되어 조회한다.
            System.out.println("findMember = " + findMember.getUsername());

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
