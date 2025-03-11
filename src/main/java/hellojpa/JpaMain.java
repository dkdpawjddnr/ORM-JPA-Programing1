package hellojpa;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity1", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("곱창");

            member.getAddressHistory().add(new Address("old1", "street", "10000"));
            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            // 값타입들은 따로 persist 안해줘도 됨.
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=======Start=======");
            Member findMember = em.find(Member.class, member.getId());

            // 값 타입 컬렉션들은 다 지연로딩
            List<Address> addressHistory = findMember.getAddressHistory();
            for(Address address : addressHistory){
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for(String favoriteFood : favoriteFoods){
                System.out.println("favoritFood = " + favoriteFood);
            }


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
